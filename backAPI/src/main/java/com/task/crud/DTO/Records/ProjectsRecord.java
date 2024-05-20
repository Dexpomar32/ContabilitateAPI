package com.task.crud.DTO.Records;

import java.sql.Date;

public record ProjectsRecord(
        String code,
        String name,
        String description,
        String status,
        Date startDate,
        Date endDate,
        String clientCode
) {}
