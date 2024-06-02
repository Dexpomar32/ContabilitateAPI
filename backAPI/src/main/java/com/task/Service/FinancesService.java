package com.task.Service;

import com.task.Model.Finances;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unchecked")
public class FinancesService {
    private final EntityManager entityManager;

    @Autowired
    public FinancesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<List<Finances>> recent() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("recentFinancialChanges");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<Finances> financesList = new ArrayList<>();

        for (Object[] result : results) {
            String type = (String) result[0];
            String code = (String) result[1];
            String date = formatDate((java.sql.Date) result[2]);
            Double amount = (Double) result[3];
            Finances finances = new Finances(type, code, date, amount);
            financesList.add(finances);
        }

        return Optional.of(financesList);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}