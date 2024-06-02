package com.task.Controller;

import com.task.DTO.Records.IncomesRecord;
import com.task.Model.Incomes;
import com.task.Service.IncomesService;
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
@RequestMapping("/incomes")
public class IncomesController {
    private final IncomesService incomesService;

    @Autowired
    public IncomesController(IncomesService incomesService) {
        this.incomesService = incomesService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<IncomesRecord>>> getAll() {
        Optional<List<IncomesRecord>> incomesRecordList = incomesService.getAll();
        return incomesRecordList.isPresent() ?
                new ResponseEntity<>(incomesRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<IncomesRecord>> getOne(@RequestParam String code) {
        Optional<IncomesRecord> incomesRecord = incomesService.getOne(code);
        return incomesRecord.isPresent() ?
                new ResponseEntity<>(incomesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<IncomesRecord>> create(@RequestBody Incomes income) {
        Optional<IncomesRecord> incomesRecord = incomesService.create(income);
        return incomesRecord.isPresent() ?
                new ResponseEntity<>(incomesRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<IncomesRecord>> update(@RequestBody Incomes income) {
        Optional<IncomesRecord> incomesRecord = incomesService.update(income);
        return incomesRecord.isPresent() ?
                new ResponseEntity<>(incomesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<IncomesRecord>> delete(@RequestParam String code) {
        Optional<IncomesRecord> incomesRecord = incomesService.delete(code);
        return incomesRecord.isPresent() ?
                new ResponseEntity<>(incomesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
