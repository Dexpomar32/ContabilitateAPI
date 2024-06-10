package com.task.Service;

import com.task.DTO.Mapper.SalesMapper;
import com.task.DTO.Records.SalesRecord;
import com.task.Model.Clients;
import com.task.Model.Sales;
import com.task.Repository.ClientsRepository;
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
public class SalesService {
    private final SalesRepository salesRepository;
    private final SalesMapper salesMapper;
    private final ClientsRepository clientsRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository, SalesMapper salesMapper, ClientsRepository clientsRepository) {
        this.salesRepository = salesRepository;
        this.salesMapper = salesMapper;
        this.clientsRepository = clientsRepository;
    }

    public Optional<List<SalesRecord>> getAll() {
        List<SalesRecord> reportsRecordList = salesRepository
                .findAll()
                .stream()
                .map(salesMapper)
                .toList();

        return Optional.of(reportsRecordList);
    }

    public Optional<SalesRecord> getOne(String code) {
        return Optional.ofNullable(salesRepository.findByCode(code))
                .map(salesMapper);
    }

    public Optional<SalesRecord> create(Sales sale) {
        if (check(sale)) {
            return Optional.empty();
        }

        if (sale.getCode() == null || sale.getCode().isEmpty()) {
            sale.setCode(generateUniqueCode());
        }

        Clients clients = clientsRepository.findByCode(sale.getClientCode());

        sale.setClient(clients);
        salesRepository.save(sale);
        return Optional.ofNullable(salesMapper.apply(sale));
    }

    public Optional<SalesRecord> update(Sales sale) {
        if (sale.getClientCode() != null && !sale.getClientCode().isEmpty()) {
            Clients clients = clientsRepository.findByCode(sale.getClientCode());
            sale.setClient(clients);
        }

        return Optional.ofNullable(salesRepository.findByCode(sale.getCode()))
                .map(existingSale -> {
                    NullAwareBeanUtils.copyNonNullProperties(sale, existingSale);
                    salesRepository.save(existingSale);
                    return salesMapper.apply(existingSale);
                });
    }

    public Optional<SalesRecord> delete(String code) {
        Optional<Sales> optionalSale = Optional.ofNullable(salesRepository.findByCode(code));

        return optionalSale.map(report -> {
            optionalSale.ifPresent(salesRepository::delete);
            return salesMapper.apply(report);
        });
    }

    public boolean check(Sales sale) {
        return Stream.of(sale.getDate(), sale.getAmount(), sale.getClientCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("SA");
        } while (salesRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
