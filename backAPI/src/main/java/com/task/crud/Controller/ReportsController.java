package com.task.crud.Controller;

import com.task.crud.DTO.Records.ReportsRecord;
import com.task.crud.Model.Reports;
import com.task.crud.Service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/reports")
public class ReportsController {
    private final ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ReportsRecord>>> getAll() {
        Optional<List<ReportsRecord>> reportsRecordList = reportsService.getAll();
        return reportsRecordList.isPresent() ?
                new ResponseEntity<>(reportsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ReportsRecord>> getOne(@RequestParam String code) {
        Optional<ReportsRecord> reportsRecord = reportsService.getOne(code);
        return reportsRecord.isPresent() ?
                new ResponseEntity<>(reportsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ReportsRecord>> create(@RequestBody Reports report) {
        Optional<ReportsRecord> reportsRecord = reportsService.create(report);
        return reportsRecord.isPresent() ?
                new ResponseEntity<>(reportsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ReportsRecord>> update(@RequestBody Reports report) {
        Optional<ReportsRecord> reportsRecord = reportsService.update(report);
        return reportsRecord.isPresent() ?
                new ResponseEntity<>(reportsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ReportsRecord>> delete(@RequestParam String code) {
        Optional<ReportsRecord> reportsRecord = reportsService.delete(code);
        return reportsRecord.isPresent() ?
                new ResponseEntity<>(reportsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
