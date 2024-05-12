package com.task.crud.DTO.Records;

import java.sql.Date;

public record NotesRecord(
        String code,
        String text,
        Date date,
        String projectCode
) {}
