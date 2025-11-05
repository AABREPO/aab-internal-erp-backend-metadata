package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.ExpensesEdit;
import com.aabuilders.Dashboard.Entity.ExpensesAudit;
import com.aabuilders.Dashboard.Entity.ExpensesForm;
import com.aabuilders.Dashboard.Repository.ExpensesAuditRepo;
import com.aabuilders.Dashboard.Repository.ExpensesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpensesService implements ExpensesServices {

    @Autowired
    private ExpensesRepo expensesRepo;

    @Autowired
    private ExpensesAuditRepo expensesAuditRepo;

    @Override
    public ExpensesForm saveForm(ExpensesForm expensesForm) {
        return expensesRepo.save(expensesForm);
    }

    @Override
    public List<ExpensesForm> getAllEntries() {
        return expensesRepo.findAll();
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
            existingExpense.setBillCopy(expensesEdit.getBillCopy());

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
            if (existingExpense.getAccountType() != null) {
                audit.setOldAccountType(existingExpense.getAccountType());
                audit.setNewAccountType(null);
                existingExpense.setAccountType(null);
            }
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
            if (existingExpense.getSource() != null) {
                audit.setOldSource(existingExpense.getSource());
                audit.setNewSource(null);
                existingExpense.setSource(null);
            }
            if (existingExpense.getPaymentMode() != null) {
                audit.setOldPaymentMode(existingExpense.getPaymentMode());
                audit.setNewPaymentMode(null);
                existingExpense.setPaymentMode(null);
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

}