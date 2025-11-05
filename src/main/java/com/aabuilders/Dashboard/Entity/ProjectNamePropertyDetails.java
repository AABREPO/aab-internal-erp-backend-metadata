package com.aabuilders.Dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectNamePropertyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectType;
    private String floorName;
    private String shopNo;
    private String doorNo;
    private String area;
    private String ebNo;
    private String ebNoFrequency;
    private String propertyTaxNo;
    private String propertyTaxFrequency;
    private String waterTaxNo;
    private String waterTaxFrequency;
    @ManyToOne
    @JoinColumn(name = "project_name_id")
    @JsonBackReference
    private ProjectNameWithAllOtherDetails projectNameWithAllOtherDetails;
}
