package com.task.DTO.Records;

public record SalesRecord(
        String code,
        String date,
        Integer price,
        String clientCode,
        String productCode
) {}
