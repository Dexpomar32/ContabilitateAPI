package com.task.crud.Service;

import com.task.crud.DTO.Mapper.ReportsMapper;
import com.task.crud.DTO.Records.ReportsRecord;
import com.task.crud.Model.Reports;
import com.task.crud.Repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ReportsService {
    public final ReportsRepository reportsRepository;
    public final ReportsMapper reportsMapper;

    @Autowired
    public ReportsService(ReportsRepository reportsRepository, ReportsMapper reportsMapper) {
        this.reportsRepository = reportsRepository;
        this.reportsMapper = reportsMapper;
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

        reportsRepository.save(report);
        return Optional.ofNullable(reportsMapper.apply(report));
    }

    public Optional<ReportsRecord> update(Reports report) {
        if (check(report)) {
            return Optional.empty();
        }

        return Optional.ofNullable(reportsRepository.findByCode(report.getCode()))
                .map(existingReport -> {
                    existingReport.setDate(report.getDate());
                    existingReport.setText(report.getText());
                    existingReport.getProject().setCode(report.getCode());
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
        return Stream.of(report.getCode(), report.getDate(), report.getText(), report.getProject().getCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
