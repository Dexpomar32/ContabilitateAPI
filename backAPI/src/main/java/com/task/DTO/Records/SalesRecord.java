package com.task.DTO.Records;

public record SalesRecord(
        String code,
        String date,
        Integer amount,
        String clientCode,
        String productCode
) {}
