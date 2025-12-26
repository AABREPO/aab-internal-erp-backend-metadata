package com.aabuilders.Dashboard.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ExpensesForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "eno")
    private int ENo;
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
//Getter and Setter
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getENo() {
        return ENo;
    }

    public void setENo(int ENo) {
        this.ENo = ENo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLabourId() {
        return labourId;
    }

    public void setLabourId(Long labourId) {
        this.labourId = labourId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMachineTools() {
        return machineTools;
    }

    public void setMachineTools(String machineTools) {
        this.machineTools = machineTools;
    }

    public String getBillCopy() {
        return billCopy;
    }

    public void setBillCopy(String billCopy) {
        this.billCopy = billCopy;
    }

    public String getDailyChecklistNo() {
        return dailyChecklistNo;
    }

    public void setDailyChecklistNo(String dailyChecklistNo) {
        this.dailyChecklistNo = dailyChecklistNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public String getUtilityTypeNumber() {
        return utilityTypeNumber;
    }

    public void setUtilityTypeNumber(String utilityTypeNumber) {
        this.utilityTypeNumber = utilityTypeNumber;
    }

    public String getUtilityForTheMonth() {
        return utilityForTheMonth;
    }

    public void setUtilityForTheMonth(String utilityForTheMonth) {
        this.utilityForTheMonth = utilityForTheMonth;
    }

    public String getUtilityValidityDays() {
        return utilityValidityDays;
    }

    public void setUtilityValidityDays(String utilityValidityDays) {
        this.utilityValidityDays = utilityValidityDays;
    }
}
