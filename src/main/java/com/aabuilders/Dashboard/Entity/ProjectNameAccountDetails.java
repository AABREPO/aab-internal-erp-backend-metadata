package com.aabuilders.Dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProjectNameAccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private String nameOfTheBank;
    private String branchOfTheBank;
    private String ifscCode;
    private String upiPhoneNumber;
    private String upiId;
    private String qrCodeUrl;
    @ManyToOne
    @JoinColumn(name = "project_name_id")  // FK column for the relationship
    @JsonBackReference
    private ProjectNameWithAllOtherDetails projectNameWithAllOtherDetails;
}
