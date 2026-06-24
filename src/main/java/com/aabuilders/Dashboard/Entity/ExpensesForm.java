package com.aabuilders.Dashboard.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ExpensesForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "eno", unique = true, nullable = false)
    private Long eNo;
    private String accountType;
    private LocalDateTime timestamp;
    private LocalDate date;
    private String siteName;
    private Long projectId;
    private String vendor;
    private Long vendorId;
    private String quantity;
    private String contractor;
    private Long contractorId;
    private Long employeeId;
    private Long labourId;
    private Long accountTypeId;
    private int amount;
    private String category;
    private String comments;
    private String machineTools;
    private String billCopy;
    private String dailyChecklistNo;
    private String source;
    private String paymentMode;
    private String utilityType;
    private String utilityTypeNumber;
    private String utilityForTheMonth;
    private String utilityValidityDays;
    private String utilityValidityType;
    private String serviceStartingDate;
    private Long branchId;
    private String enteredBy;
    private String billArrivalDate;

}
