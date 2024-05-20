package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ExpensesRecord;
import com.task.crud.Model.Expenses;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ExpensesMapper implements Function<Expenses, ExpensesRecord> {
    @Override
    public ExpensesRecord apply(Expenses expenses) {
        return new ExpensesRecord(
                expenses.getCode(),
                expenses.getDate(),
                expenses.getAmount(),
                expenses.getDescription(),
                expenses.getProject().getCode()
        );
    }
}
