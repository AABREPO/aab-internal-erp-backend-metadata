package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.ExpensesEdit;
import com.aabuilders.Dashboard.DTO.ExpensesFilterDto;
import com.aabuilders.Dashboard.Entity.ExpensesAudit;
import com.aabuilders.Dashboard.Entity.ExpensesForm;
import com.aabuilders.Dashboard.Repository.ExpensesAuditRepo;
import com.aabuilders.Dashboard.Repository.ExpensesFormSpecification;
import com.aabuilders.Dashboard.Repository.ExpensesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpensesService implements ExpensesServices {

    private static final int RECENT_LIMIT = 200;
    private static final int CHUNK_SIZE = 500;
    private static final int CHUNK_DELAY_MS = 100;

    @Autowired
    private ExpensesRepo expensesRepo;

    @Autowired
    private ExpensesAuditRepo expensesAuditRepo;

    @Transactional
    @Override
    public ExpensesForm saveForm(ExpensesForm expensesForm, Long branchId) {

        Long maxEno = expensesRepo.findMaxEnoForUpdate();

        Long nextEno = (maxEno == null) ? 1 : maxEno + 1;

        expensesForm.setENo(nextEno); // ⚠️ correct setter
        expensesForm.setBranchId(branchId);

        return expensesRepo.save(expensesForm);
    }

    @Override
    public ExpensesForm saveFormWithFixedTimestamp(ExpensesForm expensesForm, Long branchId) {
        // Fixed timestamp: 30-11-2025 at 10:30 AM
        LocalDateTime fixedTimestamp = LocalDateTime.of(2025, 11, 30, 10, 30);
        expensesForm.setTimestamp(fixedTimestamp);
        expensesForm.setBranchId(branchId);

        return expensesRepo.save(expensesForm);
    }

    @Override
    public List<ExpensesForm> getAllEntries() {
        return expensesRepo.findAll();
    }

    @Override
    public List<ExpensesForm> getLast400Entries() {
        return expensesRepo.findTop400ByOrderByIdDesc();
    }

    @Override
    public StreamingResponseBody streamFilteredExpenses(ExpensesFilterDto filter) {
        return outputStream -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Specification<ExpensesForm> spec = ExpensesFormSpecification.fromFilter(filter);
            Sort sort = Sort.by(Sort.Direction.DESC, "Id");

            List<ExpensesForm> recent = expensesRepo.findFilteredPage(spec, 0, RECENT_LIMIT, sort);
            long totalCount = expensesRepo.countFiltered(spec);

            writeStreamLine(outputStream, mapper, streamPayload("recent", recent, totalCount, 0));
            outputStream.flush();

            int offset = RECENT_LIMIT;
            List<ExpensesForm> chunk;
            int chunkIndex = 1;
            try {
                do {
                    chunk = expensesRepo.findFilteredPage(spec, offset, CHUNK_SIZE, sort);
                    if (!chunk.isEmpty()) {
                        writeStreamLine(outputStream, mapper, streamPayload("chunk", chunk, totalCount, chunkIndex));
                        outputStream.flush();
                        Thread.sleep(CHUNK_DELAY_MS);
                        chunkIndex++;
                    }
                    offset += CHUNK_SIZE;
                } while (chunk.size() == CHUNK_SIZE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new java.io.IOException("Expense filter streaming interrupted", e);
            }

            writeStreamLine(outputStream, mapper, Map.of(
                    "type", "complete",
                    "totalCount", totalCount
            ));
            outputStream.flush();
        };
    }

    private Map<String, Object> streamPayload(String type, List<ExpensesForm> data, long totalCount, int chunkIndex) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put("data", data);
        payload.put("totalCount", totalCount);
        if ("chunk".equals(type)) {
            payload.put("chunkIndex", chunkIndex);
        }
        return payload;
    }

    private void writeStreamLine(OutputStream outputStream, ObjectMapper mapper, Object payload) throws java.io.IOException {
        outputStream.write(mapper.writeValueAsString(payload).getBytes(StandardCharsets.UTF_8));
        outputStream.write('\n');
    }

    @Override
    public List<ExpensesAudit> getAllAuditsEntries(){
        return expensesAuditRepo.findAll();
    }

    @Override
    public boolean updateExpense(Long id, ExpensesEdit expensesEdit) {
        Optional<ExpensesForm> optionalExpense = expensesRepo.findById(id);
        if (optionalExpense.isPresent()) {
            ExpensesForm existingExpense = optionalExpense.get();
            String editedBy = expensesEdit.getEditedBy();
            // Save all changes in one audit record
            saveAudit(existingExpense, expensesEdit, editedBy);
            // Update the actual entity
            existingExpense.setAccountType(expensesEdit.getAccountType());
            existingExpense.setTimestamp(expensesEdit.getTimestamp());
            existingExpense.setDate(expensesEdit.getDate());
            existingExpense.setSiteName(expensesEdit.getSiteName());
            existingExpense.setProjectId(expensesEdit.getProjectId());
            existingExpense.setVendor(expensesEdit.getVendor());
            existingExpense.setVendorId(expensesEdit.getVendorId());
            existingExpense.setQuantity(expensesEdit.getQuantity());
            existingExpense.setContractor(expensesEdit.getContractor());
            existingExpense.setContractorId(expensesEdit.getContractorId());
            existingExpense.setAccountTypeId(expensesEdit.getAccountTypeId());
            existingExpense.setAmount(expensesEdit.getAmount());
            existingExpense.setCategory(expensesEdit.getCategory());
            existingExpense.setComments(expensesEdit.getComments());
            existingExpense.setMachineTools(expensesEdit.getMachineTools());
            existingExpense.setSource(expensesEdit.getSource());
            existingExpense.setPaymentMode(expensesEdit.getPaymentMode());
            existingExpense.setUtilityType(expensesEdit.getUtilityType());
            existingExpense.setUtilityTypeNumber(expensesEdit.getUtilityTypeNumber());
            existingExpense.setUtilityForTheMonth(expensesEdit.getUtilityForTheMonth());
            existingExpense.setUtilityValidityDays(expensesEdit.getUtilityValidityDays());
            existingExpense.setUtilityValidityType(expensesEdit.getUtilityValidityType());
            existingExpense.setServiceStartingDate(expensesEdit.getServiceStartingDate());
            existingExpense.setBillArrivalDate(expensesEdit.getBillArrivalDate());
            existingExpense.setBillCopy(expensesEdit.getBillCopy());
            existingExpense.setWeeklyExpensesId(expensesEdit.getWeeklyExpensesId());

            expensesRepo.save(existingExpense);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<ExpensesAudit> getAllAuditsByExpenseId(Long expenseId) {
        return expensesAuditRepo.findAllByExpenseId(expenseId);
    }

    private void saveAudit(ExpensesForm oldData, ExpensesEdit newData, String editedBy) {
        ExpensesAudit audit = new ExpensesAudit();
        audit.setExpenseId(oldData.getId());
        audit.setEditedBy(editedBy);
        audit.setEditedDate(LocalDateTime.now());
        // Existing fields
        audit.setOldSiteName(oldData.getSiteName());
        audit.setNewSiteName(newData.getSiteName());
        audit.setOldProjectId(oldData.getProjectId());
        audit.setNewProjectId(newData.getProjectId());
        audit.setOldVendor(oldData.getVendor());
        audit.setNewVendor(newData.getVendor());
        audit.setOldVendorId(oldData.getVendorId());
        audit.setNewVendorId(newData.getVendorId());
        audit.setOldContractor(oldData.getContractor());
        audit.setNewContractor(newData.getContractor());
        audit.setOldContractorId(oldData.getContractorId());
        audit.setNewContractorId(newData.getContractorId());
        audit.setOldEmployeeId(oldData.getEmployeeId());
        audit.setNewEmployeeId(newData.getEmployeeId());
        audit.setOldLabourId(oldData.getLabourId());
        audit.setNewLabourId(newData.getLabourId());
        audit.setOldAmount(oldData.getAmount() != 0 ? String.valueOf(oldData.getAmount()) : null);
        audit.setNewAmount(newData.getAmount() != 0 ? String.valueOf(newData.getAmount()) : null);
        audit.setOldAccountType(oldData.getAccountType());
        audit.setNewAccountType(newData.getAccountType());
        audit.setOldComments(oldData.getComments());
        audit.setNewComments(newData.getComments());
        audit.setOldCategory(oldData.getCategory());
        audit.setNewCategory(newData.getCategory());
        audit.setOldDate(oldData.getDate() != null ? oldData.getDate().toString() : null);
        audit.setNewDate(newData.getDate() != null ? newData.getDate().toString() : null);
        audit.setOldQuantity(oldData.getQuantity());
        audit.setNewQuantity(newData.getQuantity());
        audit.setOldMachineTools(oldData.getMachineTools());
        audit.setNewMachineTools(newData.getMachineTools());
        audit.setOldBillCopy(oldData.getBillCopy());
        audit.setNewBillCopy(newData.getBillCopy());
        audit.setOldSource(oldData.getSource());
        audit.setNewSource(newData.getSource());
        audit.setOldPaymentMode(oldData.getPaymentMode());
        audit.setNewPaymentMode(newData.getPaymentMode());
        audit.setOldUtilityType(oldData.getUtilityType());
        audit.setNewUtilityType(newData.getUtilityType());
        audit.setOldUtilityTypeNumber(oldData.getUtilityTypeNumber());
        audit.setNewUtilityTypeNumber(newData.getUtilityTypeNumber());
        audit.setOldUtilityForTheMonth(oldData.getUtilityForTheMonth());
        audit.setNewUtilityForTheMonth(newData.getUtilityForTheMonth());
        audit.setOldUtilityValidityDays(oldData.getUtilityValidityDays());
        audit.setNewUtilityValidityDays(newData.getUtilityValidityDays());
        audit.setOldUtilityValidityType(oldData.getUtilityValidityType());
        audit.setNewUtilityValidityType(newData.getUtilityValidityType());
        audit.setOldServiceStartingDate(oldData.getServiceStartingDate());
        audit.setNewServiceStartingDate(newData.getServiceStartingDate());
        audit.setOldBillArrivalDate(oldData.getBillArrivalDate());
        audit.setNewBillArrivalDate(newData.getBillArrivalDate());
        audit.setOldAccountTypeId(oldData.getAccountTypeId());
        audit.setNewAccountTypeId(newData.getAccountTypeId());
        expensesAuditRepo.save(audit);
    }

    @Override
    public boolean clearExpenseData(Long id, String editedBy) {
        Optional<ExpensesForm> optionalExpense = expensesRepo.findById(id);
        if (optionalExpense.isPresent()) {
            ExpensesForm existingExpense = optionalExpense.get();
            ExpensesAudit audit = new ExpensesAudit();
            audit.setExpenseId(existingExpense.getId());
            audit.setEditedBy(editedBy);
            audit.setEditedDate(LocalDateTime.now());
            // Audit + Clear fields
            if (existingExpense.getSiteName() != null) {
                audit.setOldSiteName(existingExpense.getSiteName());
                audit.setNewSiteName(null);
                existingExpense.setSiteName(null);
            }
            if (existingExpense.getProjectId() !=null){
                audit.setOldProjectId(existingExpense.getProjectId());
                audit.setNewProjectId(null);
                existingExpense.setProjectId(null);
            }
            if (existingExpense.getVendor() != null) {
                audit.setOldVendor(existingExpense.getVendor());
                audit.setNewVendor(null);
                existingExpense.setVendor(null);
            }
            if (existingExpense.getVendorId() !=null){
                audit.setOldVendorId(existingExpense.getVendorId());
                audit.setNewVendorId(null);
                existingExpense.setVendorId(null);
            }
            if (existingExpense.getQuantity() != null) {
                audit.setOldQuantity(existingExpense.getQuantity());
                audit.setNewQuantity(null);
                existingExpense.setQuantity(null);
            }
            if (existingExpense.getContractor() != null) {
                audit.setOldContractor(existingExpense.getContractor());
                audit.setNewContractor(null);
                existingExpense.setContractor(null);
            }
            if (existingExpense.getContractorId() !=null){
                audit.setOldContractorId(existingExpense.getContractorId());
                audit.setNewContractorId(null);
                existingExpense.setContractorId(null);
            }
            if (existingExpense.getEmployeeId() !=null){
                audit.setOldEmployeeId(existingExpense.getEmployeeId());
                audit.setNewEmployeeId(null);
                existingExpense.setEmployeeId(null);
            }
            if (existingExpense.getLabourId() !=null){
                audit.setOldLabourId(existingExpense.getLabourId());
                audit.setNewLabourId(null);
                existingExpense.setLabourId(null);
            }
            if (existingExpense.getAccountTypeId() !=null){
                audit.setOldAccountTypeId(existingExpense.getAccountTypeId());
                audit.setNewAccountTypeId(null);
                existingExpense.setAccountTypeId(null);
            }
            if (existingExpense.getAmount() != 0) {
                audit.setOldAmount(String.valueOf(existingExpense.getAmount()));
                audit.setNewAmount("0");
                existingExpense.setAmount(0);
            }
            if (existingExpense.getComments() != null) {
                audit.setOldComments(existingExpense.getComments());
                audit.setNewComments(null);
                existingExpense.setComments(null);
            }
            if (existingExpense.getCategory() != null) {
                audit.setOldCategory(existingExpense.getCategory());
                audit.setNewCategory(null);
                existingExpense.setCategory(null);
            }
            if (existingExpense.getMachineTools() != null) {
                audit.setOldMachineTools(existingExpense.getMachineTools());
                audit.setNewMachineTools(null);
                existingExpense.setMachineTools(null);
            }
            if (existingExpense.getBillCopy() != null) {
                audit.setOldBillCopy(existingExpense.getBillCopy());
                audit.setNewBillCopy(null);
                existingExpense.setBillCopy(null);
            }
            if (existingExpense.getUtilityType() != null) {
                audit.setOldUtilityType(existingExpense.getUtilityType());
                audit.setNewUtilityType(null);
                existingExpense.setUtilityType(null);
            }
            if (existingExpense.getUtilityTypeNumber() != null) {
                audit.setOldUtilityTypeNumber(existingExpense.getUtilityTypeNumber());
                audit.setNewUtilityTypeNumber(null);
                existingExpense.setUtilityTypeNumber(null);
            }
            if (existingExpense.getUtilityForTheMonth() != null) {
                audit.setOldUtilityForTheMonth(existingExpense.getUtilityForTheMonth());
                audit.setNewUtilityForTheMonth(null);
                existingExpense.setUtilityForTheMonth(null);
            }
            if (existingExpense.getUtilityValidityDays() != null) {
                audit.setOldUtilityValidityDays(existingExpense.getUtilityValidityDays());
                audit.setNewUtilityValidityDays(null);
                existingExpense.setUtilityValidityDays(null);
            }
            if (existingExpense.getUtilityValidityType() != null){
                audit.setOldUtilityValidityType(existingExpense.getUtilityValidityType());
                audit.setNewUtilityValidityType(null);
                existingExpense.setUtilityValidityType(null);
            }
            if (existingExpense.getServiceStartingDate() !=null){
                audit.setOldServiceStartingDate(existingExpense.getServiceStartingDate());
                audit.setNewServiceStartingDate(null);
                existingExpense.setServiceStartingDate(null);
            }
            if (existingExpense.getProjectId() != null){
                audit.setOldProjectId(existingExpense.getProjectId());
                audit.setNewProjectId(null);
                existingExpense.setProjectId(null);
            }
            if (existingExpense.getVendorId() !=null){
                audit.setOldVendorId(existingExpense.getVendorId());
                audit.setNewVendorId(null);
                existingExpense.setVendorId(null);
            }
            if (existingExpense.getContractorId() !=null){
                audit.setOldContractorId(existingExpense.getContractorId());
                audit.setNewContractorId(null);
                existingExpense.setContractorId(null);
            }
            // Save audit and updated expense
            expensesAuditRepo.save(audit);
            expensesRepo.save(existingExpense);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<ExpensesForm> getElectricityUtilityBills() {
        return expensesRepo.findElectricityUtilityBills();
    }
    @Override
    public List<ExpensesForm> getPropertyUtilityBills() {
        return expensesRepo.findPropertyUtilityBills();
    }
    @Override
    public List<ExpensesForm> getWaterUtilityBills(){
        return expensesRepo.findWaterUtilityBills();
    }
    @Override
    public List<ExpensesForm> getTelecomUtilityBills(){
        return expensesRepo.findTelecomUtilityBills();
    }
    @Override
    public List<ExpensesForm> getSubscriptionUtilityBills(){
        return expensesRepo.findSubscriptionUtilityBills();
    }
    @Override
    public List<ExpensesForm> getAmcUtilityBills(){
        return expensesRepo.findAmcUtilityBills();
    }
    @Override
    public List<ExpensesForm> getProfessionalUtilityBills(){
        return expensesRepo.findProfessionalUtilityBills();
    }
}