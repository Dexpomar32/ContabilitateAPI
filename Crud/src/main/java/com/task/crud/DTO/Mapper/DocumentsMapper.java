package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.DocumentsRecord;
import com.task.crud.Model.Documents;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DocumentsMapper implements Function<Documents, DocumentsRecord> {
    @Override
    public DocumentsRecord apply(Documents documents) {
        return new DocumentsRecord(
                documents.getCode(),
                documents.getType(),
                documents.getDate(),
                documents.getText(),
                documents.getClient().getCode()
        );
    }
}
