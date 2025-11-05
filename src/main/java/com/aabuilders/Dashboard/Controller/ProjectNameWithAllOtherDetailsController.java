package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.Entity.ProjectNameWithAllOtherDetails;
import com.aabuilders.Dashboard.Service.ProjectNameWithAllOtherDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectNameWithAllOtherDetailsController {

    private final ProjectNameWithAllOtherDetailsService projectService;

    public ProjectNameWithAllOtherDetailsController(ProjectNameWithAllOtherDetailsService projectService){
        this.projectService = projectService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProjectNameWithAllOtherDetails>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectNameWithAllOtherDetails> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<ProjectNameWithAllOtherDetails> createProject(@RequestBody ProjectNameWithAllOtherDetails project) {
        return ResponseEntity.ok(projectService.saveProject(project));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProjectNameWithAllOtherDetails> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectNameWithAllOtherDetails updatedProject) {
        return ResponseEntity.ok(projectService.updateProject(id, updatedProject));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload-sql")
    public ResponseEntity<String> uploadProjectSQL(@RequestParam("file") MultipartFile file) {
        String result = projectService.uploadProjectNameData(file);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/hide/{id}")
    public ResponseEntity<ProjectNameWithAllOtherDetails> updateHideStatus(
            @PathVariable Long id,
            @RequestParam boolean isHide) {

        ProjectNameWithAllOtherDetails updatedProject = projectService.updateProjectHideStatus(id, isHide);
        return ResponseEntity.ok(updatedProject);
    }

}