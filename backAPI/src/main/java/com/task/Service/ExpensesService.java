package com.task.Service;

import com.task.DTO.Mapper.ExpensesMapper;
import com.task.DTO.Records.ExpensesRecord;
import com.task.Model.Expenses;
import com.task.Model.Projects;
import com.task.Repository.ExpensesRepository;
import com.task.Repository.ProjectsRepository;
import com.task.Utils.CodeGenerator;
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
    private final ProjectsRepository projectsRepository;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository, ExpensesMapper expensesMapper, ProjectsRepository projectsRepository) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
        this.projectsRepository = projectsRepository;
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

        if (expense.getCode() == null || expense.getCode().isEmpty()) {
            expense.setCode(generateUniqueCode());
        }

        Projects projects = projectsRepository.findByCode(expense.getProjectCode());
        expense.setProject(projects);
        expensesRepository.save(expense);
        return Optional.ofNullable(expensesMapper.apply(expense));
    }

    public Optional<ExpensesRecord> update(Expenses expense) {
        if (check(expense)) {
            return Optional.empty();
        }

        Projects projects = projectsRepository.findByCode(expense.getProjectCode());
        expense.setProject(projects);

        return Optional.ofNullable(expensesRepository.findByCode(expense.getCode()))
                .map(existingExpense -> {
                    existingExpense.setDate(expense.getDate());
                    existingExpense.setAmount(expense.getAmount());
                    existingExpense.setDescription(expense.getDescription());
                    existingExpense.setProject(expense.getProject());
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
        return Stream.of(expense.getDate(), expense.getAmount(), expense.getDescription(), expense.getProjectCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode();
        } while (expensesRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
