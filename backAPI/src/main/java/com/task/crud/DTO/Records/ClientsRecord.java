package com.task.crud.DTO.Records;

public record ClientsRecord(
        String code,
        String name,
        String surname,
        String email,
        Integer number
) {}
