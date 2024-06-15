package com.task.Service;

import com.task.Model.MonthlyExpenses;
import com.task.Model.MonthlyIncomes;
import com.task.Model.MonthlySummary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            BigDecimal previousMonthIncome = (BigDecimal) result[1];
            BigDecimal currentMonthIncome = (BigDecimal) result[2];
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
            BigDecimal previousMonthExpense = (BigDecimal) result[1];
            BigDecimal currentMonthExpense = (BigDecimal) result[2];
            MonthlyExpenses monthlyExpenses = new MonthlyExpenses(day, previousMonthExpense, currentMonthExpense);
            monthlyExpenseList.add(monthlyExpenses);
        }
        return monthlyExpenseList;
    }

    public Optional<List<MonthlySummary>> summary(int month, int year) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlySummary");

        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(1, month);
        storedProcedureQuery.setParameter(2, year);

        List<MonthlySummary> monthlySummaryList = getMonthlySummary(storedProcedureQuery);

        return Optional.of(monthlySummaryList);
    }

    private static List<MonthlySummary> getMonthlySummary(StoredProcedureQuery storedProcedureQuery) {
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlySummary> monthlyExpenseList = new ArrayList<>();

        for (Object[] result : results) {
            Double monthIncome = (Double) result[0];
            Double monthExpense = (Double) result[1];
            MonthlySummary monthlySummary = new MonthlySummary(monthIncome, monthExpense);
            monthlyExpenseList.add(monthlySummary);
        }
        return monthlyExpenseList;
    }
}
