package com.veera.ppm.services;

import com.veera.ppm.domain.Project;
import com.veera.ppm.exceptions.ProjectIdException;
import com.veera.ppm.model.ProjectModel;
import com.veera.ppm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    EmployeeService employeeService;

    @Override
    public ProjectModel saveOrUpdate(ProjectModel project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            employeeService.createEmployee();
            return mapModel(projectRepository.save(map(project)));
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' is already exists");
        }
    }

    @Override
    public ProjectModel findProjectByIdentifier(String projectId) {
        Project byProjectIdentifier = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (byProjectIdentifier == null) {
            throw new ProjectIdException(String.format("Project ID '%s' doest not exist", projectId.toUpperCase()));
        }

        return mapModel(byProjectIdentifier);
    }

    @Override
    public Iterable<ProjectModel> findAllProjects() {
        return mapAllModel(projectRepository.findAll());
    }

    @Override
    public void deleteProjectByIdentifier(String projectId) {
        Project byProjectIdentifier = projectRepository.findByProjectIdentifier(projectId);
        if (byProjectIdentifier == null) {
            throw new ProjectIdException(String.format("Can not delete, Project ID '%s' doest not exist", projectId.toUpperCase()));
        }

        projectRepository.delete(byProjectIdentifier);
    }

    private Project map(@NotNull ProjectModel projectModel) {
        Project project = new Project();
        project.setId(projectModel.getId());
        project.setProjectName(projectModel.getProjectName());
        project.setProjectIdentifier(projectModel.getProjectIdentifier());
        project.setDescription(projectModel.getDescription());
        project.setStartDate(projectModel.getStartDate());
        project.setEndDate(projectModel.getEndDate());

        return project;
    }

    public ProjectModel mapModel(@NotNull Project project) {
        ProjectModel projectModel = new ProjectModel();
        projectModel.setId(project.getId());
        projectModel.setProjectName(project.getProjectName());
        projectModel.setProjectIdentifier(project.getProjectIdentifier());
        projectModel.setDescription(project.getDescription());
        projectModel.setStartDate(project.getStartDate());
        projectModel.setEndDate(project.getEndDate());
        projectModel.setCreatedDate(project.getCreated_At());
        projectModel.setUpdatedDate(project.getUpdated_At());

        return projectModel;
    }

    public Iterable<ProjectModel> mapAllModel(@NotNull Iterable<Project> projects) {
        List<ProjectModel> projectModels = new ArrayList<>();
        for (Project project : projects) {
            projectModels.add(mapModel(project));
        }

        return projectModels;
    }

}
