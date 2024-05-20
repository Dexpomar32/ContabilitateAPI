package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ProjectsRecord;
import com.task.crud.Model.Projects;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
                formatDate(projects.getStartDate()),
                formatDate(projects.getEndDate()),
                projects.getClient().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
