package com.veera.ppm.services;

import com.veera.ppm.model.ProjectModel;

public interface ProjectService {
    ProjectModel saveOrUpdate(ProjectModel project);

    ProjectModel findProjectByIdentifier(String projectId);

    Iterable<ProjectModel> findAllProjects();

    void deleteProjectByIdentifier(String projectId);
}
