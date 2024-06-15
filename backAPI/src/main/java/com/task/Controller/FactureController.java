package com.task.Controller;

import com.task.Service.Factures.ExpensesFacture;
import com.task.Service.Factures.IncomesFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/facture")
public class FactureController {
    private final IncomesFacture incomesFacture;
    private final ExpensesFacture expensesFacture;

    @Autowired
    public FactureController(IncomesFacture incomesFacture, ExpensesFacture expensesFacture) {
        this.incomesFacture = incomesFacture;
        this.expensesFacture = expensesFacture;
    }

    @GetMapping("/incomes")
    public ResponseEntity<Map<String, String>> incomes(@RequestParam Date date) throws FileNotFoundException {
        String name = incomesFacture.generate(date);
        Map<String, String> response = new HashMap<>();
        response.put("filename", name);
        if (name != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/expenses")
    public ResponseEntity<Map<String, String>> expenses(@RequestParam Date date) throws FileNotFoundException {
        String name = expensesFacture.generate(date);
        Map<String, String> response = new HashMap<>();
        response.put("filename", name);
        if (name != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/downloadPDF")
    public ResponseEntity<byte[]> downloadIncomes(@RequestParam String name) throws IOException {
        Path path = Paths.get("/ContabilitateAPI/" + name);
        byte[] fileContent = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", name);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
