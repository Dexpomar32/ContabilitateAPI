package com.task.Service;

import com.task.DTO.Mapper.IncomesMapper;
import com.task.DTO.Records.IncomesRecord;
import com.task.Model.Incomes;
import com.task.Model.Sales;
import com.task.Repository.IncomesRepository;
import com.task.Repository.SalesRepository;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class IncomesService {
    private final IncomesRepository incomesRepository;
    private final IncomesMapper incomesMapper;
    private final SalesRepository salesRepository;

    @Autowired
    public IncomesService(IncomesRepository incomesRepository, IncomesMapper incomesMapper, SalesRepository salesRepository) {
        this.incomesRepository = incomesRepository;
        this.incomesMapper = incomesMapper;
        this.salesRepository = salesRepository;
    }

    public Optional<List<IncomesRecord>> getAll() {
        List<IncomesRecord> incomesRecordList = incomesRepository
                .findAll()
                .stream()
                .map(incomesMapper)
                .toList();

        return Optional.of(incomesRecordList);
    }

    public Optional<IncomesRecord> getOne(String code) {
        return Optional.ofNullable(incomesRepository.findByCode(code))
                .map(incomesMapper);
    }

    public Optional<IncomesRecord> create(Incomes incomes) {
        if (check(incomes)) {
            return Optional.empty();
        }

        if (incomes.getCode() == null || incomes.getCode().isEmpty()) {
            incomes.setCode(generateUniqueCode());
        }

        Sales sales = salesRepository.findByCode(incomes.getSaleCode());
        incomes.setSale(sales);
        incomesRepository.save(incomes);
        return Optional.ofNullable(incomesMapper.apply(incomes));
    }

    public Optional<IncomesRecord> update(Incomes income) {
        Sales sales = salesRepository.findByCode(income.getSaleCode());
        income.setSale(sales);

        return Optional.ofNullable(incomesRepository.findByCode(income.getCode()))
                .map(existingIncome -> {
                    NullAwareBeanUtils.copyNonNullProperties(income, existingIncome);
                    incomesRepository.save(existingIncome);
                    return incomesMapper.apply(existingIncome);
                });
    }

    public Optional<IncomesRecord> delete(String code) {
        Optional<Incomes> optionalIncome = Optional.ofNullable(incomesRepository.findByCode(code));

        return optionalIncome.map(expense -> {
            optionalIncome.ifPresent(incomesRepository::delete);
            return incomesMapper.apply(expense);
        });
    }

    public boolean check(Incomes incomes) {
        return Stream.of(incomes.getDate(), incomes.getAmount(), incomes.getSaleCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("IN");
        } while (incomesRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
