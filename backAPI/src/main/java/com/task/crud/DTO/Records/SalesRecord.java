package com.task.crud.DTO.Records;

import java.sql.Date;

public record SalesRecord(
        String code,
        Date date,
        Integer amount,
        String clientCode
) {}
