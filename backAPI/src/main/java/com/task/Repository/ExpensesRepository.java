package com.task.Repository;

import com.task.Model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, String> {
    Expenses findByCode(@Param("code") String code);
}
