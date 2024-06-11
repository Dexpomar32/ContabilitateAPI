package com.task.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpenses {
    private Integer day;
    private Double previousMonthExpense;
    private Double currentMonthExpense;
}
