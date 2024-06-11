package com.task.Service;

import com.task.Model.MonthlyExpenses;
import com.task.Model.MonthlyIncomes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unchecked")
public class MonthlyFinancesService {
    private final EntityManager entityManager;

    @Autowired
    public MonthlyFinancesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<List<MonthlyIncomes>> incomes() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlyIncome");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyIncomes> monthlyProfitList = new ArrayList<>();

        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double previousMonthIncome = (Double) result[1];
            Double currentMonthIncome = (Double) result[2];
            MonthlyIncomes monthlyProfit = new MonthlyIncomes(day, previousMonthIncome, currentMonthIncome);
            monthlyProfitList.add(monthlyProfit);
        }

        return Optional.of(monthlyProfitList);
    }

    public Optional<List<MonthlyExpenses>> expenses() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlyExpenses");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyExpenses> monthlyProfitList = new ArrayList<>();

        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double previousMonthExpense = (Double) result[1];
            Double currentMonthExpense = (Double) result[2];
            MonthlyExpenses monthlyExpenses = new MonthlyExpenses(day, previousMonthExpense, currentMonthExpense);
            monthlyProfitList.add(monthlyExpenses);
        }

        return Optional.of(monthlyProfitList);
    }
}
