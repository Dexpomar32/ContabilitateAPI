package com.task.Controller;

import com.task.Model.MonthlyProfit;
import com.task.Service.MonthlyProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/monthly")
public class MonthlyProfitController {
    private final MonthlyProfitService monthlyProfitService;

    @Autowired
    public MonthlyProfitController(MonthlyProfitService monthlyProfitService) {
        this.monthlyProfitService = monthlyProfitService;
    }

    @GetMapping("/profit")
    public ResponseEntity<Optional<List<MonthlyProfit>>> profit() {
        Optional<List<MonthlyProfit>> optionalMonthlyProfitList = monthlyProfitService.profit();
        return optionalMonthlyProfitList.isPresent() ?
                new ResponseEntity<>(optionalMonthlyProfitList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
