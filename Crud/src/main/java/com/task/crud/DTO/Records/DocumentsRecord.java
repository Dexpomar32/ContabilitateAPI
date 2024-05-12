package com.task.crud.DTO.Records;

import java.sql.Date;

public record DocumentsRecord(
        String code,
        String type,
        Date date,
        String text,
        String clientCode
) {}
