package com.task.Controller;

import com.task.DTO.Records.ContractsRecord;
import com.task.Model.Contracts;
import com.task.Service.ContractsService;
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
@RequestMapping("/api/v1/contracts")
public class ContractsController {
    private final ContractsService contractsService;

    @Autowired
    public ContractsController(ContractsService contractsService) {
        this.contractsService = contractsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ContractsRecord>>> getAll() {
        Optional<List<ContractsRecord>> contractsRecordList = contractsService.getAll();
        return contractsRecordList.isPresent() ?
                new ResponseEntity<>(contractsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ContractsRecord>> getOne(@RequestParam String code) {
        Optional<ContractsRecord> contractsRecord = contractsService.getOne(code);
        return contractsRecord.isPresent() ?
                new ResponseEntity<>(contractsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ContractsRecord>> create(@RequestBody Contracts contract) {
        Optional<ContractsRecord> contractsRecord = contractsService.create(contract);
        return contractsRecord.isPresent() ?
                new ResponseEntity<>(contractsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ContractsRecord>> update(@RequestBody Contracts contract) {
        Optional<ContractsRecord> contractsRecord = contractsService.update(contract);
        return contractsRecord.isPresent() ?
                new ResponseEntity<>(contractsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ContractsRecord>> delete(@RequestParam String code) {
        Optional<ContractsRecord> contractsRecord = contractsService.delete(code);
        return contractsRecord.isPresent() ?
                new ResponseEntity<>(contractsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/switch")
    public ResponseEntity<String> switchAccount(@RequestParam String code) {
        Boolean contractsRecord = contractsService.findByClientCode(code);
        return contractsRecord ?
                ResponseEntity.ok("Switched to active") :
                ResponseEntity.ok("Switched to not active");
    }
}
