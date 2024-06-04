package com.task.DTO.Mapper;

import com.task.Model.Notes;
import com.task.DTO.Records.NotesRecord;
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