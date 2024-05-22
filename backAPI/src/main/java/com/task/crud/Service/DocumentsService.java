package com.task.crud.Service;

import com.task.crud.DTO.Mapper.DocumentsMapper;
import com.task.crud.DTO.Records.DocumentsRecord;
import com.task.crud.Model.Clients;
import com.task.crud.Model.Documents;
import com.task.crud.Repository.ClientsRepository;
import com.task.crud.Repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final DocumentsMapper documentsMapper;
    private final ClientsRepository clientsRepository;

    @Autowired
    public DocumentsService(DocumentsRepository documentsRepository, DocumentsMapper documentsMapper, ClientsRepository clientsRepository) {
        this.documentsRepository = documentsRepository;
        this.documentsMapper = documentsMapper;
        this.clientsRepository = clientsRepository;
    }

    public Optional<List<DocumentsRecord>> getAll() {
        List<DocumentsRecord> documentsRecordList = documentsRepository
                .findAll()
                .stream()
                .map(documentsMapper)
                .toList();

        return Optional.of(documentsRecordList);
    }

    public Optional<DocumentsRecord> getOne(String code) {
        return Optional.ofNullable(documentsRepository.findByCode(code))
                .map(documentsMapper);
    }

    public Optional<DocumentsRecord> create(Documents document) {
        if (check(document)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(document.getClientCode());
        document.setClient(client);
        documentsRepository.save(document);
        return Optional.ofNullable(documentsMapper.apply(document));
    }

    public Optional<DocumentsRecord> update(Documents document) {
        if (check(document)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(document.getClientCode());
        document.setClient(client);

        return Optional.ofNullable(documentsRepository.findByCode(document.getCode()))
                .map(existingDocument -> {
                    existingDocument.setType(document.getType());
                    existingDocument.setDate(document.getDate());
                    existingDocument.setText(document.getText());
                    existingDocument.setClient(document.getClient());
                    documentsRepository.save(existingDocument);
                    return documentsMapper.apply(existingDocument);
                });
    }

    public Optional<DocumentsRecord> delete(String code) {
        Optional<Documents> optionalDocument = Optional.ofNullable(documentsRepository.findByCode(code));

        return optionalDocument.map(document -> {
            optionalDocument.ifPresent(documentsRepository::delete);
            return documentsMapper.apply(document);
        });
    }

    public boolean check(Documents document) {
        return Stream.of(document.getCode(), document.getType(), document.getDate(), document.getText(), document.getClientCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
