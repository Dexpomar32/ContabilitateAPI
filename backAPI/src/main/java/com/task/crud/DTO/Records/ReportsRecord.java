package com.task.crud.DTO.Records;

import java.sql.Date;

public record ReportsRecord(
        String code,
        Date date,
        String text,
        String projectName
) {}
