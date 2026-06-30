package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.ProjectNameWithAllOtherDetails;
import com.aabuilders.Dashboard.Repository.ProjectNameWithAllOtherDetailsRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ProjectNameWithAllOtherDetailsService {
    private final ProjectNameWithAllOtherDetailsRepository projectRepository;

    public ProjectNameWithAllOtherDetailsService(ProjectNameWithAllOtherDetailsRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectNameWithAllOtherDetails> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<ProjectNameWithAllOtherDetails> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public ProjectNameWithAllOtherDetails saveProject(ProjectNameWithAllOtherDetails project) {
        // Set back-reference for owner details
        if (project.getOwnerDetails() != null) {
            project.getOwnerDetails().forEach(owner -> owner.setProjectNameWithAllOtherDetails(project));
        }

        // Set back-reference for property details
        if (project.getPropertyDetails() != null) {
            project.getPropertyDetails().forEach(property -> property.setProjectNameWithAllOtherDetails(project));
        }
        if (project.getAccountDetails() != null) {
            project.getAccountDetails().forEach(account ->
                    account.setProjectNameWithAllOtherDetails(project)
            );
        }

        return projectRepository.save(project);
    }
    public ProjectNameWithAllOtherDetails updateProject(Long id, ProjectNameWithAllOtherDetails updatedProject) {
        return projectRepository.findById(id)
                .map(existing -> {
                    existing.setProjectName(updatedProject.getProjectName());
                    existing.setProjectAddress(updatedProject.getProjectAddress());
                    existing.setProjectId(updatedProject.getProjectId());
                    existing.setProjectCategory(updatedProject.getProjectCategory());
                    existing.setProjectReferenceName(updatedProject.getProjectReferenceName());
                    existing.setLocation(updatedProject.getLocation());
                    existing.setBranch(updatedProject.getBranch());
                    existing.setSiteEngineerId(updatedProject.getSiteEngineerId());
                    existing.setStatus(updatedProject.getStatus());
                    // --- Replace owner details ---
                    existing.getOwnerDetails().clear();
                    if (updatedProject.getOwnerDetails() != null) {
                        updatedProject.getOwnerDetails().forEach(owner -> {
                            owner.setProjectNameWithAllOtherDetails(existing);
                            existing.getOwnerDetails().add(owner);
                        });
                    }

                    // --- Replace property details ---
                    existing.getPropertyDetails().clear();
                    if (updatedProject.getPropertyDetails() != null) {
                        updatedProject.getPropertyDetails().forEach(property -> {
                            property.setProjectNameWithAllOtherDetails(existing);
                            existing.getPropertyDetails().add(property);
                        });
                    }

                    // --- Replace account details ---
                    existing.getAccountDetails().clear();
                    if (updatedProject.getAccountDetails() !=null){
                        updatedProject.getAccountDetails().forEach(account ->{
                            account.setProjectNameWithAllOtherDetails(existing);
                            existing.getAccountDetails().add(account);
                        });
                    }

                    return projectRepository.saveAndFlush(existing); // <-- immediate DB sync
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectNameWithAllOtherDetails updateProjectHideStatus(Long id, boolean isHide) {
        ProjectNameWithAllOtherDetails project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setHide(isHide);
        return projectRepository.save(project);
    }

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String uploadProjectNameData(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty. Please upload a valid SQL file.";
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            String filename = file.getOriginalFilename();

            if (filename == null || !filename.endsWith(".sql")) {
                return "Unsupported file type. Please upload a .sql file.";
            }

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("INSERT INTO `project_name_with_all_other_details`")) {

                    // Extract column names
                    int startIdx = line.indexOf('(') + 1;
                    int endIdx = line.indexOf(')');
                    String columnsPart = line.substring(startIdx, endIdx)
                            .replace("`", "")
                            .trim();
                    String[] columns = columnsPart.split(",");

                    // Extract values part
                    String valuesPart = line.substring(line.indexOf("VALUES") + 6).trim();
                    valuesPart = valuesPart.replaceAll(";$", "").trim();

                    // Split multiple records
                    String[] records = valuesPart.split("\\),\\s*\\(");

                    for (String record : records) {
                        record = record.replaceAll("[()']", "");
                        String[] fields = record.split(",");

                        Map<String, String> dataMap = new HashMap<>();
                        for (int i = 0; i < columns.length && i < fields.length; i++) {
                            dataMap.put(columns[i].trim(), fields[i].trim());
                        }

                        Long id = null;
                        try {
                            if (dataMap.containsKey("id") && !dataMap.get("id").isEmpty()) {
                                id = Long.parseLong(dataMap.get("id"));
                            }
                        } catch (NumberFormatException ignored) {}

                        // Build entity
                        ProjectNameWithAllOtherDetails project = new ProjectNameWithAllOtherDetails();
                        project.setProjectName(dataMap.getOrDefault("project_name", ""));
                        project.setProjectAddress(dataMap.getOrDefault("project_address", ""));
                        project.setProjectId(dataMap.getOrDefault("project_id", ""));
                        project.setProjectCategory(dataMap.getOrDefault("project_category", ""));
                        project.setProjectReferenceName(dataMap.getOrDefault("project_reference_name", ""));

                        if (id != null) {
                            project.setId(id);

                            // ✅ check if exists
                            ProjectNameWithAllOtherDetails existing = entityManager.find(ProjectNameWithAllOtherDetails.class, id);

                            if (existing != null) {
                                // update
                                existing.setProjectName(project.getProjectName());
                                existing.setProjectAddress(project.getProjectAddress());
                                existing.setProjectId(project.getProjectId());
                                existing.setProjectCategory(project.getProjectCategory());
                                existing.setProjectReferenceName(project.getProjectReferenceName());
                                entityManager.merge(existing);
                            } else {
                                // insert new record with given id
                                entityManager.createNativeQuery(
                                                "INSERT INTO project_name_with_all_other_details " +
                                                        "(id, project_name, project_address, project_id, project_category, project_reference_name) " +
                                                        "VALUES (:id, :projectName, :projectAddress, :projectId, :projectCategory, :projectReferenceName)")
                                        .setParameter("id", id)
                                        .setParameter("projectName", project.getProjectName())
                                        .setParameter("projectAddress", project.getProjectAddress())
                                        .setParameter("projectId", project.getProjectId())
                                        .setParameter("projectCategory", project.getProjectCategory())
                                        .setParameter("projectReferenceName", project.getProjectReferenceName())
                                        .executeUpdate();
                            }
                        } else {
                            // ✅ no ID (should never happen from SQL file but safe)
                            projectRepository.save(project);
                        }
                    }
                }
            }

            entityManager.flush();
            return "✅ File uploaded successfully! Records processed.";

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to upload file: " + e.getMessage();
        }
    }
}
