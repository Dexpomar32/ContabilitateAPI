package com.task.DTO.Mapper;

import com.task.DTO.Records.IncomesRecord;
import com.task.Model.Incomes;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class IncomesMapper implements Function<Incomes, IncomesRecord> {
    @Override
    public IncomesRecord apply(Incomes incomes) {
        return new IncomesRecord(
                incomes.getCode(),
                formatDate(incomes.getDate()),
                incomes.getAmount(),
                incomes.getSale().getCode()
        );
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
