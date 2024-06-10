package com.task.Service;

import com.task.Model.MonthlyProfit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetMonthlyIncome");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<MonthlyProfit> monthlyProfitList = new ArrayList<>();

        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double previousMonthIncome = (Double) result[1];
            Double currentMonthIncome = (Double) result[2];
            MonthlyProfit monthlyProfit = new MonthlyProfit(day, previousMonthIncome, currentMonthIncome);
            monthlyProfitList.add(monthlyProfit);
        }

        return Optional.of(monthlyProfitList);
    }
}
