package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.DTO.ExpensesEdit;
import com.aabuilders.Dashboard.DTO.ExpensesAuditDto;
import com.aabuilders.Dashboard.DTO.ExpensesFilterDto;
import com.aabuilders.Dashboard.DTO.ExpensesFormDto;
import com.aabuilders.Dashboard.Entity.ExpensesAudit;
import com.aabuilders.Dashboard.Entity.ExpensesForm;
import com.aabuilders.Dashboard.Service.ExpensesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/expenses_form")
public class ExpensesController {
    @Autowired
    private ExpensesServices expensesServices;

    @PostMapping("/save_fixed")
    public ResponseEntity<String> addExpensesFormEntryWithFixedTimestamp(
            @RequestBody ExpensesFormDto expensesFormDto) {
        try {
            if (expensesFormDto.getBranchId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("branchId is required");
            }
            ExpensesForm expensesForm = new ExpensesForm();
            expensesForm.setAccountType(expensesFormDto.getAccountType());
            expensesForm.setAccountTypeId(expensesFormDto.getAccountTypeId());
            expensesForm.setDate(expensesFormDto.getDate());
            expensesForm.setSiteName(expensesFormDto.getSiteName());
            expensesForm.setProjectId(expensesFormDto.getProjectId());
            expensesForm.setVendor(expensesFormDto.getVendor());
            expensesForm.setVendorId(expensesFormDto.getVendorId());
            expensesForm.setQuantity(expensesFormDto.getQuantity());
            expensesForm.setContractor(expensesFormDto.getContractor());
            expensesForm.setContractorId(expensesFormDto.getContractorId());
            expensesForm.setEmployeeId(expensesFormDto.getEmployeeId());
            expensesForm.setLabourId(expensesFormDto.getLabourId());
            expensesForm.setAmount(expensesFormDto.getAmount());
            expensesForm.setPaymentMode(expensesFormDto.getPaymentMode());
            expensesForm.setCategory(expensesFormDto.getCategory());
            expensesForm.setComments(expensesFormDto.getComments());
            expensesForm.setMachineTools(expensesFormDto.getMachineTools());
            expensesForm.setBillCopy(expensesFormDto.getBillCopyUrl());
            expensesForm.setSource(expensesFormDto.getSource());
            expensesForm.setUtilityType(expensesFormDto.getUtilityType());
            expensesForm.setUtilityTypeNumber(expensesFormDto.getUtilityTypeNumber());
            expensesForm.setUtilityForTheMonth(expensesFormDto.getUtilityForTheMonth());
            expensesForm.setUtilityValidityDays(expensesFormDto.getUtilityValidityDays());
            expensesForm.setUtilityValidityType(expensesFormDto.getUtilityValidityType());
            expensesForm.setServiceStartingDate(expensesFormDto.getServiceStartingDate());
            expensesForm.setBillArrivalDate(expensesFormDto.getBillArrivalDate());
            expensesForm.setWeeklyExpensesId(expensesFormDto.getWeeklyExpensesId());
            // Save with FIXED TIMESTAMP (30-11-2025)
            expensesServices.saveFormWithFixedTimestamp(expensesForm, expensesFormDto.getBranchId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Expense saved successfully with fixed timestamp (30-11-2025).");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid data or error while saving.");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> addExpensesFormEntry(@RequestBody ExpensesFormDto expensesFormDto) {
        try {
            if (expensesFormDto.getBranchId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("branchId is required");
            }
            ExpensesForm expensesForm = new ExpensesForm();
            expensesForm.setAccountType(expensesFormDto.getAccountType());
            expensesForm.setAccountTypeId(expensesFormDto.getAccountTypeId());
            expensesForm.setTimestamp(LocalDateTime.now()); // Set the current date and time
            expensesForm.setDate(expensesFormDto.getDate());
            expensesForm.setSiteName(expensesFormDto.getSiteName());
            expensesForm.setProjectId(expensesFormDto.getProjectId());
            expensesForm.setVendor(expensesFormDto.getVendor());
            expensesForm.setVendorId(expensesFormDto.getVendorId());
            expensesForm.setQuantity(expensesFormDto.getQuantity());
            expensesForm.setContractor(expensesFormDto.getContractor());
            expensesForm.setContractorId(expensesFormDto.getContractorId());
            expensesForm.setEmployeeId(expensesFormDto.getEmployeeId());
            expensesForm.setLabourId(expensesFormDto.getLabourId());
            expensesForm.setAmount(expensesFormDto.getAmount());
            expensesForm.setPaymentMode(expensesFormDto.getPaymentMode());
            expensesForm.setCategory(expensesFormDto.getCategory());
            expensesForm.setComments(expensesFormDto.getComments());
            expensesForm.setMachineTools(expensesFormDto.getMachineTools());
            expensesForm.setBillCopy(expensesFormDto.getBillCopyUrl());
            expensesForm.setSource(expensesFormDto.getSource());
            expensesForm.setUtilityType(expensesFormDto.getUtilityType());
            expensesForm.setUtilityTypeNumber(expensesFormDto.getUtilityTypeNumber());
            expensesForm.setUtilityForTheMonth(expensesFormDto.getUtilityForTheMonth());
            expensesForm.setUtilityValidityDays(expensesFormDto.getUtilityValidityDays());
            expensesForm.setUtilityValidityType(expensesFormDto.getUtilityValidityType());
            expensesForm.setServiceStartingDate(expensesFormDto.getServiceStartingDate());
            expensesForm.setBillArrivalDate(expensesFormDto.getBillArrivalDate());
            expensesForm.setEnteredBy(expensesFormDto.getEnteredBy());
            expensesForm.setWeeklyExpensesId(expensesFormDto.getWeeklyExpensesId());

            ExpensesForm saved = expensesServices.saveForm(expensesForm, expensesFormDto.getBranchId());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format or other error");
        }
    }
    @GetMapping("/get_form")
    public ResponseEntity<List<ExpensesForm>> getAllExpensesFormEntry() {
        List<ExpensesForm> expensesFormsEntries = expensesServices.getAllEntries();
        return ResponseEntity.ok().body(expensesFormsEntries);
    }

    @GetMapping("/get/last_400")
    public ResponseEntity<List<ExpensesForm>> getLast400ExpensesFormEntries() {
        return ResponseEntity.ok(expensesServices.getLast400Entries());
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseEntity<StreamingResponseBody> filterExpenses(@RequestBody ExpensesFilterDto filter) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(expensesServices.streamFilteredExpenses(filter));
    }
    @GetMapping("/get/full_history")
    public ResponseEntity<List<ExpensesAudit>> getAllAuditEntry(){
        List<ExpensesAudit> expensesAudits = expensesServices.getAllAuditsEntries();
        return ResponseEntity.ok().body(expensesAudits);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpensesEdit expensesEdit
    ) {
        boolean isUpdated = expensesServices.updateExpense(id, expensesEdit); // ✅ only id and expensesEdit
        if (isUpdated) {
            return ResponseEntity.ok("Expense updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }
    }
    @GetMapping("/audit/{id}")
    public ResponseEntity<List<ExpensesAuditDto>> getExpenseAudit(@PathVariable Long id) {
        List<ExpensesAudit> audits = expensesServices.getAllAuditsByExpenseId(id);
        List<ExpensesAuditDto> auditDtos = audits.stream().map(audit -> {
            ExpensesAuditDto dto = new ExpensesAuditDto();
            dto.setId(audit.getId());
            dto.setExpenseId(audit.getExpenseId());
            dto.setEditedBy(audit.getEditedBy());
            dto.setEditedDate(audit.getEditedDate());
            dto.setOldSiteName(audit.getOldSiteName());
            dto.setNewSiteName(audit.getNewSiteName());
            dto.setOldProjectId(audit.getOldProjectId());
            dto.setNewProjectId(audit.getNewProjectId());
            dto.setOldVendor(audit.getOldVendor());
            dto.setNewVendor(audit.getNewVendor());
            dto.setOldVendorId(audit.getOldVendorId());
            dto.setNewVendorId(audit.getNewVendorId());
            dto.setOldContractor(audit.getOldContractor());
            dto.setNewContractor(audit.getNewContractor());
            dto.setOldContractorId(audit.getOldContractorId());
            dto.setNewContractorId(audit.getNewContractorId());
            dto.setOldEmployeeId(audit.getOldEmployeeId());
            dto.setNewEmployeeId(audit.getNewEmployeeId());
            dto.setOldLabourId(audit.getOldLabourId());
            dto.setNewLabourId(audit.getNewLabourId());
            dto.setOldAccountTypeId(audit.getOldAccountTypeId());
            dto.setNewAccountTypeId(audit.getNewAccountTypeId());
            dto.setOldBillArrivalDate(audit.getOldBillArrivalDate());
            dto.setNewBillArrivalDate(audit.getNewBillArrivalDate());
            dto.setOldDate(audit.getOldDate());
            dto.setNewDate(audit.getNewDate());
            dto.setOldAccountType(audit.getOldAccountType());
            dto.setNewAccountType(audit.getNewAccountType());
            dto.setOldQuantity(audit.getOldQuantity());
            dto.setNewQuantity(audit.getNewQuantity());
            dto.setOldAmount(audit.getOldAmount());
            dto.setNewAmount(audit.getNewAmount());
            dto.setOldCategory(audit.getOldCategory());
            dto.setNewCategory(audit.getNewCategory());
            dto.setOldComments(audit.getOldComments());
            dto.setNewComments(audit.getNewComments());
            dto.setOldMachineTools(audit.getOldMachineTools());
            dto.setNewMachineTools(audit.getNewMachineTools());
            dto.setOldBillCopy(audit.getOldBillCopy());
            dto.setNewBillCopy(audit.getNewBillCopy());
            dto.setOldUtilityTypeNumber(audit.getOldUtilityTypeNumber());
            dto.setNewUtilityTypeNumber(audit.getNewUtilityTypeNumber());
            dto.setOldUtilityType(audit.getOldUtilityType());
            dto.setNewUtilityType(audit.getNewUtilityType());
            dto.setOldUtilityValidityType(audit.getOldUtilityValidityType());
            dto.setNewUtilityValidityType(audit.getNewUtilityValidityType());
            dto.setOldUtilityValidityDays(audit.getOldUtilityValidityDays());
            dto.setNewUtilityValidityDays(audit.getNewUtilityValidityDays());
            dto.setOldPaymentMode(audit.getOldPaymentMode());
            dto.setNewPaymentMode(audit.getNewPaymentMode());
            dto.setOldServiceStartingDate(audit.getOldServiceStartingDate());
            dto.setNewServiceStartingDate(audit.getNewServiceStartingDate());
            dto.setOldUtilityForTheMonth(audit.getOldUtilityForTheMonth());
            dto.setNewUtilityForTheMonth(audit.getNewUtilityForTheMonth());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(auditDtos);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> clearExpense(@PathVariable Long id, @RequestParam String editedBy) {
        boolean result = expensesServices.clearExpenseData(id, editedBy);
        if (result) {
            return ResponseEntity.ok("Expense data cleared successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }
    }
    @GetMapping("/utility/electricity")
    public List<ExpensesForm> getElectricityUtilityBills() {
        return expensesServices.getElectricityUtilityBills();
    }
    @GetMapping("/utility/property")
    public List<ExpensesForm> getPropertyUtilityBills(){
        return expensesServices.getPropertyUtilityBills();
    }
    @GetMapping("/utility/water")
    public List<ExpensesForm> getWaterUtilityBills(){
        return expensesServices.getWaterUtilityBills();
    }
    @GetMapping("/utility/telecom")
    public List<ExpensesForm> getTelecomUtilityBills(){
        return expensesServices.getTelecomUtilityBills();
    }
    @GetMapping("/utility/subscription")
    public List<ExpensesForm> getSubscriptionUtilityBills(){
        return expensesServices.getSubscriptionUtilityBills();
    }
    @GetMapping("/utility/amc")
    public List<ExpensesForm> getAmcUtilityBills(){
        return expensesServices.getAmcUtilityBills();
    }
    @GetMapping("/utility/profession")
    public List<ExpensesForm> getProfessionalUtilityBills(){
        return expensesServices.getProfessionalUtilityBills();
    }
}