package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.NotesRecord;
import com.task.crud.Model.Notes;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NotesMapper implements Function<Notes, NotesRecord> {
    @Override
    public NotesRecord apply(Notes notes) {
        return new NotesRecord(
                notes.getCode(),
                notes.getText(),
                notes.getDate(),
                notes.getProject().getCode()
        );
    }
}
