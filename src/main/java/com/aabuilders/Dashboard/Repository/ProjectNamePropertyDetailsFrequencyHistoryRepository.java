package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ProjectNamePropertyDetailsFrequencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNamePropertyDetailsFrequencyHistoryRepository extends JpaRepository<ProjectNamePropertyDetailsFrequencyHistory, Long> {
}