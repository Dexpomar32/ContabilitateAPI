package com.task.Controller;

import com.task.DTO.Records.DocumentsRecord;
import com.task.Model.Documents;
import com.task.Service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentsController {
    private final DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<DocumentsRecord>>> getAll() {
        Optional<List<DocumentsRecord>> documentsRecordList = documentsService.getAll();
        return documentsRecordList.isPresent() ?
                new ResponseEntity<>(documentsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<DocumentsRecord>> getOne(@RequestParam String code) {
        Optional<DocumentsRecord> documentsRecord = documentsService.getOne(code);
        return documentsRecord.isPresent() ?
                new ResponseEntity<>(documentsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<DocumentsRecord>> create(@RequestBody Documents document) {
        Optional<DocumentsRecord> documentsRecord = documentsService.create(document);
        return documentsRecord.isPresent() ?
                new ResponseEntity<>(documentsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<DocumentsRecord>> update(@RequestBody Documents document) {
        Optional<DocumentsRecord> documentsRecord = documentsService.update(document);
        return documentsRecord.isPresent() ?
                new ResponseEntity<>(documentsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<DocumentsRecord>> delete(@RequestParam String code) {
        Optional<DocumentsRecord> documentsRecord = documentsService.delete(code);
        return documentsRecord.isPresent() ?
                new ResponseEntity<>(documentsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
