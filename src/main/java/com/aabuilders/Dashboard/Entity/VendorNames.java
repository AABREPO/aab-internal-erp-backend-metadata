package com.aabuilders.Dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class VendorNames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vendorName;
    @JsonProperty("account_holder_name")
    private String accountHolderName;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("ifsc_code")
    private String ifscCode;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("upi_id")
    private String upiId;
    @JsonProperty("gpay_number")
    private String gpayNumber;
    @JsonProperty("contact_number")
    private String contactNumber;
    @JsonProperty("contact_email")
    private String contactEmail;
    @Lob
    @Column(name = "upi_qr_image", columnDefinition = "LONGBLOB")
    @JsonProperty("upi_qr_image")
    private byte[] upiQRImage;
    private boolean makeAsServiceShop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getGpayNumber() {
        return gpayNumber;
    }

    public void setGpayNumber(String gpayNumber) {
        this.gpayNumber = gpayNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public byte[] getUpiQRImage() {
        return upiQRImage;
    }

    public void setUpiQRImage(byte[] upiQRImage) {
        this.upiQRImage = upiQRImage;
    }

    public boolean isMakeAsServiceShop() {
        return makeAsServiceShop;
    }

    public void setMakeAsServiceShop(boolean makeAsServiceShop) {
        this.makeAsServiceShop = makeAsServiceShop;
    }
}
