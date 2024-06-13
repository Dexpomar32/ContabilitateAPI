package com.task.DTO.Records;

public record SalesRecord(
        String code,
        String date,
        Double price,
        String clientCode,
        String productCode
) {}
