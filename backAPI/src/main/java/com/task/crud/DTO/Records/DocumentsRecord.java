package com.task.crud.DTO.Records;

public record DocumentsRecord(
        String code,
        String type,
        String date,
        String text,
        String clientCode
) {}
