package com.task.Controller;

import com.task.Service.Factures.IncomesFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Optional;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/facture")
public class FactureController {
    private final IncomesFacture incomesFacture;

    @Autowired
    public FactureController(IncomesFacture incomesFacture) {
        this.incomesFacture = incomesFacture;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<String>> getAll() throws FileNotFoundException {
        incomesFacture.generate("INC011");
        Optional<String> expensesRecordList = "sdfsd".describeConstable();
        return expensesRecordList.isPresent() ?
                new ResponseEntity<>(expensesRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
