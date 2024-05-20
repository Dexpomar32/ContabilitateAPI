package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ReportsRecord;
import com.task.crud.Model.Reports;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReportsMapper implements Function<Reports, ReportsRecord> {
    @Override
    public ReportsRecord apply(Reports reports) {
        return new ReportsRecord(
                reports.getCode(),
                reports.getDate(),
                reports.getText(),
                reports.getProject().getCode()
        );
    }
}
