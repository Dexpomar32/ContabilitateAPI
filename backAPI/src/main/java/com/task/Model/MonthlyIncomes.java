package com.task.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyIncomes {
    private Integer day;
    private BigDecimal previousMonthIncome;
    private BigDecimal currentMonthIncome;
}
