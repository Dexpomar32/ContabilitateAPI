package com.task.crud.DTO.Records;

public record ProjectsRecord(
        String code,
        String name,
        String description,
        String status,
        String startDate,
        String endDate,
        String clientCode
) {}
