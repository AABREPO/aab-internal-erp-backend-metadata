package com.aabuilders.Dashboard.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ExpensesFormDto {
    private String accountType;
    @JsonProperty("eno")
    private int eno;
    private LocalDate date;
    private String siteName;
    private Long projectId;
    private String vendor;
    private Long vendorId;
    private String quantity;
    private String contractor;
    private Long contractorId;
    private Long accountTypeId;
    private Long employeeId;
    private Long labourId;
    private int amount;
    private String category;
    private String comments;
    private String machineTools;
    private String billCopyUrl;
    private String source;
    private String utilityType;
    private String utilityTypeNumber;
    private String utilityForTheMonth;
    private String utilityValidityDays;
    private String utilityValidityType;
    private String serviceStartingDate;
    private String paymentMode;
    private Long branchId;
    private String enteredBy;
    private String billArrivalDate;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
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

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
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

    public String getBillCopyUrl() {
        return billCopyUrl;
    }

    public void setBillCopyUrl(String billCopyUrl) {
        this.billCopyUrl = billCopyUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getUtilityValidityType() {
        return utilityValidityType;
    }

    public void setUtilityValidityType(String utilityValidityType) {
        this.utilityValidityType = utilityValidityType;
    }

    public String getServiceStartingDate() {
        return serviceStartingDate;
    }

    public void setServiceStartingDate(String serviceStartingDate) {
        this.serviceStartingDate = serviceStartingDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getBillArrivalDate() {
        return billArrivalDate;
    }

    public void setBillArrivalDate(String billArrivalDate) {
        this.billArrivalDate = billArrivalDate;
    }
}
