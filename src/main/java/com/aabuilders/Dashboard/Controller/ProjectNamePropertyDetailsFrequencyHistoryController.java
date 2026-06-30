package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.Entity.ProjectNamePropertyDetailsFrequencyHistory;
import com.aabuilders.Dashboard.Service.ProjectNamePropertyDetailsFrequencyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frequency-history")
public class ProjectNamePropertyDetailsFrequencyHistoryController {

    @Autowired
    private ProjectNamePropertyDetailsFrequencyHistoryService frequencyHistoryService;

    @PostMapping("/save")
    public ProjectNamePropertyDetailsFrequencyHistory savePropertyDetailsFrequencyHistory(@RequestBody ProjectNamePropertyDetailsFrequencyHistory propertyDetailsFrequencyHistory){
        return frequencyHistoryService.saveAllPropertyFrequencyHistory(propertyDetailsFrequencyHistory);
    }
    @GetMapping("/getAll")
    public List<ProjectNamePropertyDetailsFrequencyHistory> getAllPropertyDetailsFrequencyHistory(){
        return frequencyHistoryService.getAllPropertyFrequencyHistory();
    }
}
