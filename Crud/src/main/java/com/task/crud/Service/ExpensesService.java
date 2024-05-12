package com.task.crud.Service;

import com.task.crud.DTO.Mapper.ExpensesMapper;
import com.task.crud.DTO.Records.ExpensesRecord;
import com.task.crud.Model.Expenses;
import com.task.crud.Repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final ExpensesMapper expensesMapper;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository, ExpensesMapper expensesMapper) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
    }

    public Optional<List<ExpensesRecord>> getAll() {
        List<ExpensesRecord> expensesRecordList = expensesRepository
                .findAll()
                .stream()
                .map(expensesMapper)
                .toList();

        return Optional.of(expensesRecordList);
    }

    public Optional<ExpensesRecord> getOne(String code) {
        return Optional.ofNullable(expensesRepository.findByCode(code))
                .map(expensesMapper);
    }

    public Optional<ExpensesRecord> create(Expenses expense) {
        if (check(expense)) {
            return Optional.empty();
        }

        expensesRepository.save(expense);
        return Optional.ofNullable(expensesMapper.apply(expense));
    }

    public Optional<ExpensesRecord> update(Expenses expense) {
        if (check(expense)) {
            return Optional.empty();
        }

        return Optional.ofNullable(expensesRepository.findByCode(expense.getCode()))
                .map(existingExpense -> {
                    existingExpense.setDate(expense.getDate());
                    existingExpense.setAmount(expense.getAmount());
                    existingExpense.setDescription(expense.getDescription());
                    existingExpense.getProject().setCode(expense.getCode());
                    expensesRepository.save(existingExpense);
                    return expensesMapper.apply(existingExpense);
                });
    }

    public Optional<ExpensesRecord> delete(String code) {
        Optional<Expenses> optionalExpense = Optional.ofNullable(expensesRepository.findByCode(code));

        return optionalExpense.map(expense -> {
            optionalExpense.ifPresent(expensesRepository::delete);
            return expensesMapper.apply(expense);
        });
    }

    public boolean check(Expenses expense) {
        return Stream.of(expense.getCode(), expense.getDate(), expense.getAmount(), expense.getDescription(), expense.getProject().getCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
