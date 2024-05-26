package com.task.DTO.Mapper;

import com.task.DTO.Records.SalesRecord;
import com.task.Model.Sales;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class SalesMapper implements Function<Sales, SalesRecord> {
    @Override
    public SalesRecord apply(Sales sales) {
        return new SalesRecord(
                sales.getCode(),
                formatDate(sales.getDate()),
                sales.getAmount(),
                sales.getClient().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
