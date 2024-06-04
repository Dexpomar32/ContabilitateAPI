package com.task.Service;

import com.task.Model.Finances;
import com.task.Model.MonthlyProfit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unchecked")
public class MonthlyProfitService {
    private final EntityManager entityManager;

    @Autowired
    public MonthlyProfitService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<List<MonthlyProfit>> profit() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("CalculateMonthlyProfit");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyProfit> monthlyProfitList = new ArrayList<>();

        for (Object[] result : results) {
            BigDecimal currentMonthIncome = (BigDecimal) result[0];
            BigDecimal previousMonthIncome = (BigDecimal) result[1];
            MonthlyProfit monthlyProfit = new MonthlyProfit(currentMonthIncome, previousMonthIncome);
            monthlyProfitList.add(monthlyProfit);
        }

        return Optional.of(monthlyProfitList);
    }
}
