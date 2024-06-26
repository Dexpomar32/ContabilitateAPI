package com.task.Service;

import com.task.DTO.Mapper.SalesMapper;
import com.task.DTO.Records.SalesRecord;
import com.task.Model.*;
import com.task.Repository.ClientsRepository;
import com.task.Repository.ProductsRepository;
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
    private final IncomesService incomesService;
    private final ProductsRepository productsRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository, SalesMapper salesMapper,
                        ClientsRepository clientsRepository, IncomesService incomesService,
                        ProductsRepository productsRepository) {
        this.salesRepository = salesRepository;
        this.salesMapper = salesMapper;
        this.clientsRepository = clientsRepository;
        this.incomesService = incomesService;
        this.productsRepository = productsRepository;
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
        Products products = productsRepository.findByCode(sale.getProductCode());
        Incomes incomes = new Incomes();
        incomes.setDate(sale.getDate());
        incomes.setAmount(sale.getPrice());
        incomes.setSaleCode(sale.getCode());
        sale.setClient(clients);
        sale.setProduct(products);
        salesRepository.save(sale);
        incomesService.create(incomes);
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

    public Optional<List<SalesRecord>> history(String code) {
        List<SalesRecord> reportsRecordList = salesRepository
                .history(code)
                .stream()
                .map(salesMapper)
                .toList();

        return Optional.of(reportsRecordList);
    }

    public boolean check(Sales sale) {
        return Stream.of(sale.getDate(), sale.getPrice())
                .anyMatch(Objects::isNull);
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("SA");
        } while (salesRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
