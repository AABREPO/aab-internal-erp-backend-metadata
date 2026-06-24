package com.aabuilders.Dashboard.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses_audit")
@Getter
@Setter
public class ExpensesAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long oldEmployeeId;
    private Long newEmployeeId;
    private Long oldLabourId;
    private Long newLabourId;
    private String oldDate;
    private String newDate;
    private String oldAccountType;
    private String newAccountType;
    private Long oldAccountTypeId;
    private Long newAccountTypeId;
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
    private String oldSource;
    private String newSource;
    private String oldPaymentMode;
    private String newPaymentMode;
    private String oldUtilityType;
    private String newUtilityType;
    private String oldUtilityTypeNumber;
    private String newUtilityTypeNumber;
    private String oldUtilityForTheMonth;
    private String newUtilityForTheMonth;
    private String oldUtilityValidityDays;
    private String newUtilityValidityDays;
    private String oldUtilityValidityType;
    private String newUtilityValidityType;
    private String oldServiceStartingDate;
    private String newServiceStartingDate;
    private String oldBillArrivalDate;
    private String newBillArrivalDate;
}
