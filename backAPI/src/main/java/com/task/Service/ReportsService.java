package com.task.Service;

import com.task.DTO.Mapper.ReportsMapper;
import com.task.DTO.Records.ReportsRecord;
import com.task.Model.Projects;
import com.task.Model.Reports;
import com.task.Repository.ProjectsRepository;
import com.task.Repository.ReportsRepository;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ReportsService {
    private final ReportsRepository reportsRepository;
    private final ReportsMapper reportsMapper;
    private final ProjectsRepository projectsRepository;

    @Autowired
    public ReportsService(ReportsRepository reportsRepository, ReportsMapper reportsMapper, ProjectsRepository projectsRepository) {
        this.reportsRepository = reportsRepository;
        this.reportsMapper = reportsMapper;
        this.projectsRepository = projectsRepository;
    }

    public Optional<List<ReportsRecord>> getAll() {
        List<ReportsRecord> reportsRecordList = reportsRepository
                .findAll()
                .stream()
                .map(reportsMapper)
                .toList();

        return Optional.of(reportsRecordList);
    }

    public Optional<ReportsRecord> getOne(String code) {
        return Optional.ofNullable(reportsRepository.findByCode(code))
                .map(reportsMapper);
    }

    public Optional<ReportsRecord> create(Reports report) {
        if (check(report)) {
            return Optional.empty();
        }

        if (report.getCode() == null || report.getCode().isEmpty()) {
            report.setCode(generateUniqueCode());
        }

        Projects projects = projectsRepository.findByCode(report.getProjectCode());
        report.setProject(projects);
        reportsRepository.save(report);
        return Optional.ofNullable(reportsMapper.apply(report));
    }

    public Optional<ReportsRecord> update(Reports report) {
        Projects projects = projectsRepository.findByCode(report.getProjectCode());
        report.setProject(projects);

        return Optional.ofNullable(reportsRepository.findByCode(report.getCode()))
                .map(existingReport -> {
                    NullAwareBeanUtils.copyNonNullProperties(report, existingReport);
                    reportsRepository.save(existingReport);
                    return reportsMapper.apply(existingReport);
                });
    }

    public Optional<ReportsRecord> delete(String code) {
        Optional<Reports> optionalReport = Optional.ofNullable(reportsRepository.findByCode(code));

        return optionalReport.map(report -> {
            optionalReport.ifPresent(reportsRepository::delete);
            return reportsMapper.apply(report);
        });
    }

    public boolean check(Reports report) {
        return Stream.of(report.getDate(), report.getText(), report.getProjectCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("RE");
        } while (reportsRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
