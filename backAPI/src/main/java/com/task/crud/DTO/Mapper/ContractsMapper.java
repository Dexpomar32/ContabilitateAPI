package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ContractsRecord;
import com.task.crud.Model.Contracts;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class ContractsMapper implements Function<Contracts, ContractsRecord> {
    @Override
    public ContractsRecord apply(Contracts contracts) {
        return new ContractsRecord(
                contracts.getCode(),
                formatDate(contracts.getDate()),
                formatDate(contracts.getPeriod()),
                contracts.getClient().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
