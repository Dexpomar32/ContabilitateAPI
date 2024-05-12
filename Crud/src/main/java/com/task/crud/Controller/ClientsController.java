package com.task.crud.Controller;

import com.task.crud.DTO.Records.ClientsRecord;
import com.task.crud.Model.Clients;
import com.task.crud.Service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/clients")
public class ClientsController {
    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ClientsRecord>>> getAll() {
        Optional<List<ClientsRecord>> clientsRecordList = clientsService.getAll();
        return clientsRecordList.isPresent() ?
                new ResponseEntity<>(clientsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ClientsRecord>> getOne(@RequestParam String code) {
        Optional<ClientsRecord> clientsRecord = clientsService.getOne(code);
        return clientsRecord.isPresent() ?
                new ResponseEntity<>(clientsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ClientsRecord>> create(@RequestBody Clients client) {
        Optional<ClientsRecord> clientsRecord = clientsService.create(client);
        return clientsRecord.isPresent() ?
                new ResponseEntity<>(clientsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ClientsRecord>> update(@RequestBody Clients client) {
        Optional<ClientsRecord> clientsRecord = clientsService.update(client);
        return clientsRecord.isPresent() ?
                new ResponseEntity<>(clientsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ClientsRecord>> delete(@RequestParam String code) {
        Optional<ClientsRecord> clientsRecord = clientsService.delete(code);
        return clientsRecord.isPresent() ?
                new ResponseEntity<>(clientsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
