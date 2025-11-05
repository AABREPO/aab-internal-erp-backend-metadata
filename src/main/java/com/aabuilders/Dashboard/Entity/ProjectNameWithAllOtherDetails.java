package com.aabuilders.Dashboard.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class ProjectNameWithAllOtherDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String projectName;
    private String projectAddress;
    private String projectId;
    private String projectCategory;
    private String projectReferenceName;
    private boolean isHide;
    @OneToMany(mappedBy = "projectNameWithAllOtherDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectNameOwnerDetails> ownerDetails;

    @OneToMany(mappedBy = "projectNameWithAllOtherDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectNamePropertyDetails> propertyDetails;

}
