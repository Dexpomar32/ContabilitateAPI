package com.task.Controller;

import com.task.Model.Finances;
import com.task.Service.FinancesService;
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
@RequestMapping("/finances")
public class FinancesController {
    private final FinancesService financesService;

    @Autowired
    public FinancesController(FinancesService financesService) {
        this.financesService = financesService;
    }

    @GetMapping("/recent")
    public ResponseEntity<Optional<List<Finances>>> recent() {
        Optional<List<Finances>> optionalFinancesList = financesService.recent();
        return optionalFinancesList.isPresent() ?
                new ResponseEntity<>(optionalFinancesList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}