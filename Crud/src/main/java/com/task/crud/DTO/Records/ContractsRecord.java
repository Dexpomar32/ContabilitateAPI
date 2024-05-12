package com.task.crud.DTO.Records;

import java.sql.Date;

public record ContractsRecord(
        String code,
        Date date,
        Date period,
        String clientCode
) {}
