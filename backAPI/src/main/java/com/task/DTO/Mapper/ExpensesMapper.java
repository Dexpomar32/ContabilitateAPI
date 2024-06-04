package com.task.DTO.Mapper;

import com.task.DTO.Records.ExpensesRecord;
import com.task.Model.Expenses;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class ExpensesMapper implements Function<Expenses, ExpensesRecord> {
    @Override
    public ExpensesRecord apply(Expenses expenses) {
        return new ExpensesRecord(
                expenses.getCode(),
                formatDate(expenses.getDate()),
                expenses.getAmount(),
                expenses.getDescription(),
                expenses.getProject().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}