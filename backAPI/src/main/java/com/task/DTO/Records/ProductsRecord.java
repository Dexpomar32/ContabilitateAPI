package com.task.DTO.Records;

public record ProductsRecord(
        String code,
        String name,
        String description,
        Double price,
        Integer quantity
) {}