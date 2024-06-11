package com.task.DTO.Records;

public record ExpensesRecord(
        String code,
        String date,
        Double amount,
        String description,
        String projectCode
) {}
