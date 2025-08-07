package com.aabuilders.Dashboard.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ExpensesFormDto {
    private String accountType;
    @JsonProperty("eno")
    private int eno;
    private LocalDate date;
    private String siteName;
    private String vendor;
    private String quantity;
    private String contractor;
    private int amount;
    private String category;
    private String comments;
    private String machineTools;
    private String billCopyUrl;

    // Getters
    public String getAccountType() { return accountType; }
    public int getEno() { return eno; }
    public LocalDate getDate() { return date; }
    public String getSiteName() { return siteName; }
    public String getVendor() { return vendor; }
    public String getQuantity() { return quantity; }
    public String getContractor() { return contractor; }
    public int getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getComments() { return comments; }
    public String getMachineTools() { return machineTools; }
    public String getBillCopyUrl() { return billCopyUrl; }

    // Setters
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public void setEno(int eno) { this.eno = eno; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setContractor(String contractor) { this.contractor = contractor; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setComments(String comments) { this.comments = comments; }
    public void setMachineTools(String machineTools) { this.machineTools = machineTools; }
    public void setBillCopyUrl(String billCopyUrl) { this.billCopyUrl = billCopyUrl; }
}
