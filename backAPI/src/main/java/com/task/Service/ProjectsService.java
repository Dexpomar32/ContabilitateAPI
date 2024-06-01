package com.task.Service;

import com.task.DTO.Mapper.ProjectsMapper;
import com.task.DTO.Records.ProjectsRecord;
import com.task.Model.Clients;
import com.task.Model.CountResponse;
import com.task.Model.Projects;
import com.task.Repository.ClientsRepository;
import com.task.Repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ProjectsMapper projectsMapper;
    private final ClientsRepository clientsRepository;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper, ClientsRepository clientsRepository) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
        this.clientsRepository = clientsRepository;
    }

    public Optional<List<ProjectsRecord>> getAll() {
        List<ProjectsRecord> projectsRecordList = projectsRepository
                .findAll()
                .stream()
                .map(projectsMapper)
                .toList();

        return Optional.of(projectsRecordList);
    }

    public Optional<ProjectsRecord> getOne(String code) {
        return Optional.ofNullable(projectsRepository.findByCode(code))
                .map(projectsMapper);
    }

    public Optional<ProjectsRecord> create(Projects project) {
        if (check(project)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(project.getClientCode());
        project.setClient(client);
        projectsRepository.save(project);
        return Optional.ofNullable(projectsMapper.apply(project));
    }

    public Optional<ProjectsRecord> update(Projects project) {
        if (check(project)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(project.getClientCode());
        project.setClient(client);

        return Optional.ofNullable(projectsRepository.findByCode(project.getCode()))
                .map(existingProject -> {
                    existingProject.setName(project.getName());
                    existingProject.setDescription(project.getDescription());
                    existingProject.setStatus(project.getStatus());
                    existingProject.setStartDate(project.getStartDate());
                    existingProject.setEndDate(project.getEndDate());
                    existingProject.setClient(project.getClient());
                    projectsRepository.save(existingProject);
                    return projectsMapper.apply(existingProject);
                });
    }

    public Optional<ProjectsRecord> delete(String code) {
        Optional<Projects> optionalProject = Optional.ofNullable(projectsRepository.findByCode(code));

        return optionalProject.map(client -> {
            optionalProject.ifPresent(projectsRepository::delete);
            return projectsMapper.apply(client);
        });
    }

    public Optional<CountResponse> count() {
        Long count = projectsRepository.count();
        return Optional.of(new CountResponse(count));
    }

    public boolean check(Projects project) {
        return Stream.of(project.getCode(), project.getName(), project.getDescription(), project.getStatus(), project.getStartDate(),
                        project.getEndDate(), project.getClientCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
