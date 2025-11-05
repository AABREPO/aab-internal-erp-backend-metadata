package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ProjectNameOwnerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNameOwnerDetailsRepository extends JpaRepository<ProjectNameOwnerDetails, Long> {
}
