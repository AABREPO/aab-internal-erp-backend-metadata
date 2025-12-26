package com.aabuilders.Dashboard.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ExpensesAuditDto {

    private Long id;
    private Long expenseId;
    private String editedBy;
    private LocalDateTime editedDate;
    private String oldSiteName;
    private String newSiteName;
    private Long oldProjectId;
    private Long newProjectId;
    private String oldVendor;
    private String newVendor;
    private Long oldVendorId;
    private Long newVendorId;
    private String oldContractor;
    private String newContractor;
    private Long oldContractorId;
    private Long newContractorId;
    private String oldDate;
    private String newDate;
    private String oldAccountType;
    private String newAccountType;
    private String oldQuantity;
    private String newQuantity;
    private String oldAmount;
    private String newAmount;
    private String oldCategory;
    private String newCategory;
    private String oldComments;
    private String newComments;
    private String oldMachineTools;
    private String newMachineTools;
    private String oldBillCopy;
    private String newBillCopy;


}
