package com.task.Controller;

import com.task.DTO.Records.SalesRecord;
import com.task.Model.Sales;
import com.task.Service.SalesService;
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
@RequestMapping("/sales")
public class SalesController {
    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<SalesRecord>>> getAll() {
        Optional<List<SalesRecord>> salesRecordList = salesService.getAll();
        return salesRecordList.isPresent() ?
                new ResponseEntity<>(salesRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<SalesRecord>> getOne(@RequestParam String code) {
        Optional<SalesRecord> salesRecord = salesService.getOne(code);
        return salesRecord.isPresent() ?
                new ResponseEntity<>(salesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<SalesRecord>> create(@RequestBody Sales sale) {
        Optional<SalesRecord> salesRecord = salesService.create(sale);
        return salesRecord.isPresent() ?
                new ResponseEntity<>(salesRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<SalesRecord>> update(@RequestBody Sales sale) {
        Optional<SalesRecord> salesRecord = salesService.update(sale);
        return salesRecord.isPresent() ?
                new ResponseEntity<>(salesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<SalesRecord>> delete(@RequestParam String code) {
        Optional<SalesRecord> salesRecord = salesService.delete(code);
        return salesRecord.isPresent() ?
                new ResponseEntity<>(salesRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
