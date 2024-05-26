package com.task.Controller;

import com.task.DTO.Records.ProjectsRecord;
import com.task.Model.Projects;
import com.task.Service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjectsService projectsService;

    @Autowired
    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ProjectsRecord>>> getAll() {
        Optional<List<ProjectsRecord>> projectsRecordList = projectsService.getAll();
        return projectsRecordList.isPresent() ?
                new ResponseEntity<>(projectsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ProjectsRecord>> getOne(@RequestParam String code) {
        Optional<ProjectsRecord> projectsRecord = projectsService.getOne(code);
        return projectsRecord.isPresent() ?
                new ResponseEntity<>(projectsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ProjectsRecord>> create(@RequestBody Projects project) {
        Optional<ProjectsRecord> projectsRecord = projectsService.create(project);
        return projectsRecord.isPresent() ?
                new ResponseEntity<>(projectsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ProjectsRecord>> update(@RequestBody Projects project) {
        Optional<ProjectsRecord> projectsRecord = projectsService.update(project);
        return projectsRecord.isPresent() ?
                new ResponseEntity<>(projectsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ProjectsRecord>> delete(@RequestParam String code) {
        Optional<ProjectsRecord> projectsRecord = projectsService.delete(code);
        return projectsRecord.isPresent() ?
                new ResponseEntity<>(projectsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
