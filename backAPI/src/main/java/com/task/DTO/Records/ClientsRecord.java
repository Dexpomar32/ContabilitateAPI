package com.task.DTO.Records;

public record ClientsRecord(
        String code,
        String name,
        String surname,
        String email,
        Long number
) {}
