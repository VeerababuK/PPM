package com.veera.ppm.web;

import com.veera.ppm.model.ProjectModel;
import com.veera.ppm.services.ProjectService;
import com.veera.ppm.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("")
    public ResponseEntity<?> crateProject(@Valid @RequestBody ProjectModel project, BindingResult result) {
        ResponseEntity<?> errorsIfExist = validationService.getErrorsIfExist(result);
        if (errorsIfExist != null) {
            return errorsIfExist;
        }
        ProjectModel dbProject = projectService.saveOrUpdate(project);
        return new ResponseEntity<>(dbProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectModel> getProjectById(@PathVariable String projectId) {
        ProjectModel projectByIdentifier = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<>(projectByIdentifier, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<ProjectModel>> getAllProjects() {
        Iterable<ProjectModel> allProjects = projectService.findAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteByProjectId(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId.toUpperCase());
        return new ResponseEntity<>(String.format("Project with ID '%s' got deleted.", projectId.toUpperCase()), HttpStatus.OK);
    }

}
