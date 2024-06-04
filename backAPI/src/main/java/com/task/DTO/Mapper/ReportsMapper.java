package com.task.DTO.Mapper;

import com.task.Model.Reports;
import com.task.DTO.Records.ReportsRecord;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class ReportsMapper implements Function<Reports, ReportsRecord> {
    @Override
    public ReportsRecord apply(Reports reports) {
        return new ReportsRecord(
                reports.getCode(),
                formatDate(reports.getDate()),
                reports.getText(),
                reports.getProject().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}