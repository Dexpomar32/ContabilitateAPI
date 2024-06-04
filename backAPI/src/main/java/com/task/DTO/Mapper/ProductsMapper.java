package com.task.DTO.Mapper;

import com.task.DTO.Records.ProductsRecord;
import com.task.Model.Products;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductsMapper implements Function<Products, ProductsRecord> {
    @Override
    public ProductsRecord apply(Products products) {
        return new ProductsRecord(
                products.getCode(),
                products.getName(),
                products.getDescription(),
                products.getPrice(),
                products.getQuantity()
        );
    }
}