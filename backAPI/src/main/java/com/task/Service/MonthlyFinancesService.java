package com.task.Service;

import com.task.Model.MonthlyExpenses;
import com.task.Model.MonthlyIncomes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
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

    public Optional<List<MonthlyIncomes>> incomes(int month, int year) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlyIncome");

        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(1, month);
        storedProcedureQuery.setParameter(2, year);

        List<MonthlyIncomes> monthlyProfitList = getMonthlyIncomes(storedProcedureQuery);

        return Optional.of(monthlyProfitList);
    }

    private static List<MonthlyIncomes> getMonthlyIncomes(StoredProcedureQuery storedProcedureQuery) {
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyIncomes> monthlyProfitList = new ArrayList<>();

        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double previousMonthIncome = (Double) result[1];
            Double currentMonthIncome = (Double) result[2];
            MonthlyIncomes monthlyProfit = new MonthlyIncomes(day, previousMonthIncome, currentMonthIncome);
            monthlyProfitList.add(monthlyProfit);
        }
        return monthlyProfitList;
    }

    public Optional<List<MonthlyExpenses>> expenses(int month, int year) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlyExpenses");

        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(1, month);
        storedProcedureQuery.setParameter(2, year);

        List<MonthlyExpenses> monthlyExpenseList = getMonthlyExpenses(storedProcedureQuery);

        return Optional.of(monthlyExpenseList);
    }

    private static List<MonthlyExpenses> getMonthlyExpenses(StoredProcedureQuery storedProcedureQuery) {
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyExpenses> monthlyExpenseList = new ArrayList<>();

        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double previousMonthExpense = (Double) result[1];
            Double currentMonthExpense = (Double) result[2];
            MonthlyExpenses monthlyExpenses = new MonthlyExpenses(day, previousMonthExpense, currentMonthExpense);
            monthlyExpenseList.add(monthlyExpenses);
        }
        return monthlyExpenseList;
    }
}
