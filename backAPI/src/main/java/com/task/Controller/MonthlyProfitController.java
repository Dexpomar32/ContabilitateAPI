package com.task.Controller;

import com.task.Model.MonthlyExpenses;
import com.task.Model.MonthlyIncomes;
import com.task.Service.MonthlyFinancesService;
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
    private final MonthlyFinancesService monthlyProfitService;

    @Autowired
    public MonthlyProfitController(MonthlyFinancesService monthlyProfitService) {
        this.monthlyProfitService = monthlyProfitService;
    }

    @GetMapping("/income")
    public ResponseEntity<Optional<List<MonthlyIncomes>>> incomes() {
        Optional<List<MonthlyIncomes>> optionalMonthlyIncomesList = monthlyProfitService.incomes();
        return optionalMonthlyIncomesList.isPresent() ?
                new ResponseEntity<>(optionalMonthlyIncomesList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/expenses")
    public ResponseEntity<Optional<List<MonthlyExpenses>>> expenses() {
        Optional<List<MonthlyExpenses>> optionalMonthlyExpensesList = monthlyProfitService.expenses();
        return optionalMonthlyExpensesList.isPresent() ?
                new ResponseEntity<>(optionalMonthlyExpensesList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
