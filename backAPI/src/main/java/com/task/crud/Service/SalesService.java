package com.task.crud.Service;

import com.task.crud.DTO.Mapper.SalesMapper;
import com.task.crud.DTO.Records.SalesRecord;
import com.task.crud.Model.Clients;
import com.task.crud.Model.Sales;
import com.task.crud.Repository.ClientsRepository;
import com.task.crud.Repository.SalesRepository;
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

        Clients clients = clientsRepository.findByCode(sale.getClientCode());
        sale.setClient(clients);
        salesRepository.save(sale);
        return Optional.ofNullable(salesMapper.apply(sale));
    }

    public Optional<SalesRecord> update(Sales sale) {
        if (check(sale)) {
            return Optional.empty();
        }

        Clients clients = clientsRepository.findByCode(sale.getClientCode());
        sale.setClient(clients);

        return Optional.ofNullable(salesRepository.findByCode(sale.getCode()))
                .map(existingSale -> {
                    existingSale.setDate(sale.getDate());
                    existingSale.setAmount(sale.getAmount());
                    existingSale.setClient(sale.getClient());
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
        return Stream.of(sale.getCode(), sale.getDate(), sale.getAmount(), sale.getClientCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
