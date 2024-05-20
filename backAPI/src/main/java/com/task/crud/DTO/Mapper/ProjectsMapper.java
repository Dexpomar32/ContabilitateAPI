package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ProjectsRecord;
import com.task.crud.Model.Projects;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProjectsMapper implements Function<Projects, ProjectsRecord> {
    @Override
    public ProjectsRecord apply(Projects projects) {
        return new ProjectsRecord(
                projects.getCode(),
                projects.getName(),
                projects.getDescription(),
                projects.getStatus(),
                projects.getStartDate(),
                projects.getEndDate(),
                projects.getClient().getCode()
        );
    }
}
