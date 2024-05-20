package com.task.crud.DTO.Records;

public record SalesRecord(
        String code,
        String date,
        Integer amount,
        String clientCode
) {}
