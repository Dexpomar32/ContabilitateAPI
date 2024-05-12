package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.SalesRecord;
import com.task.crud.Model.Sales;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SalesMapper implements Function<Sales, SalesRecord> {
    @Override
    public SalesRecord apply(Sales sales) {
        return new SalesRecord(
                sales.getCode(),
                sales.getDate(),
                sales.getAmount(),
                sales.getClient().getCode()
        );
    }
}
