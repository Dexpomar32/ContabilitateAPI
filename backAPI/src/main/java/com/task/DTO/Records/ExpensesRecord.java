package com.task.DTO.Records;

public record ExpensesRecord(
        String code,
        String date,
        Integer amount,
        String description,
        String projectCode
) {}
