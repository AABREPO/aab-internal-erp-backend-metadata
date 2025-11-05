package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.ProjectNamePropertyDetailsFrequencyHistory;
import com.aabuilders.Dashboard.Repository.ProjectNamePropertyDetailsFrequencyHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectNamePropertyDetailsFrequencyHistoryService {

    @Autowired
    private ProjectNamePropertyDetailsFrequencyHistoryRepository frequencyHistoryRepository;

    public ProjectNamePropertyDetailsFrequencyHistory saveAllPropertyFrequencyHistory(ProjectNamePropertyDetailsFrequencyHistory propertyDetailsFrequencyHistory){
        return frequencyHistoryRepository.save(propertyDetailsFrequencyHistory);
    }
    public List<ProjectNamePropertyDetailsFrequencyHistory> getAllPropertyFrequencyHistory(){
        return frequencyHistoryRepository.findAll();
    }
}
