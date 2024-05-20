package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.NotesRecord;
import com.task.crud.Model.Notes;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class NotesMapper implements Function<Notes, NotesRecord> {
    @Override
    public NotesRecord apply(Notes notes) {
        return new NotesRecord(
                notes.getCode(),
                notes.getText(),
                formatDate(notes.getDate()),
                notes.getProject().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
