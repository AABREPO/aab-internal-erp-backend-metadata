package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ProjectNameWithAllOtherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNameWithAllOtherDetailsRepository extends JpaRepository<ProjectNameWithAllOtherDetails, Long> {
}
