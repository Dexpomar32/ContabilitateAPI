package com.task.crud.DTO.Records;

import java.sql.Date;

public record ExpensesRecord(
        String code,
        Date date,
        Integer amount,
        String description,
        String projectCode
) {}
