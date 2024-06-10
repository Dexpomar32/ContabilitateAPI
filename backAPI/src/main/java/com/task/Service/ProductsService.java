package com.task.Service;

import com.task.DTO.Mapper.ProductsMapper;
import com.task.DTO.Records.ProductsRecord;
import com.task.Model.CountResponse;
import com.task.Model.Products;
import com.task.Repository.ProductsRepository;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    public Optional<List<ProductsRecord>> getAll() {
        List<ProductsRecord> productsRecordList = productsRepository
                .findAll()
                .stream()
                .map(productsMapper)
                .toList();

        return Optional.of(productsRecordList);
    }

    public Optional<ProductsRecord> getOne(String code) {
        return Optional.ofNullable(productsRepository.findByCode(code))
                .map(productsMapper);
    }

    public Optional<ProductsRecord> create(Products product) {
        if (check(product)) {
            return Optional.empty();
        }

        if (product.getCode() == null || product.getCode().isEmpty()) {
            product.setCode(generateUniqueCode());
        }

        productsRepository.save(product);
        return Optional.ofNullable(productsMapper.apply(product));
    }

    public Optional<ProductsRecord> update(Products product) {
        return Optional.ofNullable(productsRepository.findByCode(product.getCode()))
                .map(existingProduct -> {
                    NullAwareBeanUtils.copyNonNullProperties(product, existingProduct);
                    productsRepository.save(existingProduct);
                    return productsMapper.apply(existingProduct);
                });
    }

    public Optional<ProductsRecord> delete(String code) {
        Optional<Products> optionalProduct = Optional.ofNullable(productsRepository.findByCode(code));

        return optionalProduct.map(products -> {
            optionalProduct.ifPresent(productsRepository::delete);
            return productsMapper.apply(products);
        });
    }

    public Optional<CountResponse> count() {
        Long count = productsRepository.count();
        return Optional.of(new CountResponse(count));
    }

    public boolean check(Products product) {
        return Stream.of(product.getName(), product.getDescription(), product.getPrice(), product.getQuantity())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("PRD");
        } while (productsRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
