package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ProjectNamePropertyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNamePropertyDetailsRepository extends JpaRepository<ProjectNamePropertyDetails, Long> {
}
