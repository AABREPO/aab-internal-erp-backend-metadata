package com.aabuilders.Dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectNameOwnerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;
    private String fatherName;
    private String mobile;
    private String age;
    private String emailId;
    private String clientAddress;
    @ManyToOne
    @JoinColumn(name = "project_name_id")  // FK column for the relationship
    @JsonBackReference
    private ProjectNameWithAllOtherDetails projectNameWithAllOtherDetails;
}
