package com.task.DTO.Mapper;

import com.task.DTO.Records.DocumentsRecord;
import com.task.Model.Documents;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class DocumentsMapper implements Function<Documents, DocumentsRecord> {
    @Override
    public DocumentsRecord apply(Documents documents) {
        return new DocumentsRecord(
                documents.getCode(),
                documents.getType(),
                formatDate(documents.getDate()),
                documents.getText(),
                documents.getClient().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
