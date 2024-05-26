package com.task.Controller;

import com.task.DTO.Records.ExpensesRecord;
import com.task.Model.Expenses;
import com.task.Service.ExpensesService;
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
@RequestMapping("/expenses")
public class ExpensesController {
    private final ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ExpensesRecord>>> getAll() {
        Optional<List<ExpensesRecord>> expensesRecordList = expensesService.getAll();
        return expensesRecordList.isPresent() ?
                new ResponseEntity<>(expensesRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ExpensesRecord>> getOne(@RequestParam String code) {
        Optional<ExpensesRecord> expensesRecord = expensesService.getOne(code);
        return expensesRecord.isPresent() ?
                new ResponseEntity<>(expensesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ExpensesRecord>> create(@RequestBody Expenses expense) {
        Optional<ExpensesRecord> expensesRecord = expensesService.create(expense);
        return expensesRecord.isPresent() ?
                new ResponseEntity<>(expensesRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ExpensesRecord>> update(@RequestBody Expenses expense) {
        Optional<ExpensesRecord> expensesRecord = expensesService.update(expense);
        return expensesRecord.isPresent() ?
                new ResponseEntity<>(expensesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ExpensesRecord>> delete(@RequestParam String code) {
        Optional<ExpensesRecord> expensesRecord = expensesService.delete(code);
        return expensesRecord.isPresent() ?
                new ResponseEntity<>(expensesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
