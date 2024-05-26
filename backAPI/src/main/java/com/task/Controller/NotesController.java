package com.task.Controller;

import com.task.DTO.Records.NotesRecord;
import com.task.Model.Notes;
import com.task.Service.NotesService;
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
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<NotesRecord>>> getAll() {
        Optional<List<NotesRecord>> notesRecordList = notesService.getAll();
        return notesRecordList.isPresent() ?
                new ResponseEntity<>(notesRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<NotesRecord>> getOne(@RequestParam String code) {
        Optional<NotesRecord> notesRecord = notesService.getOne(code);
        return notesRecord.isPresent() ?
                new ResponseEntity<>(notesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<NotesRecord>> create(@RequestBody Notes note) {
        Optional<NotesRecord> notesRecord = notesService.create(note);
        return notesRecord.isPresent() ?
                new ResponseEntity<>(notesRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<NotesRecord>> update(@RequestBody Notes note) {
        Optional<NotesRecord> notesRecord = notesService.update(note);
        return notesRecord.isPresent() ?
                new ResponseEntity<>(notesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<NotesRecord>> delete(@RequestParam String code) {
        Optional<NotesRecord> notesRecord = notesService.delete(code);
        return notesRecord.isPresent() ?
                new ResponseEntity<>(notesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
