package com.task.Repository;

import com.task.Model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, String> {
    Expenses findByCode(@Param("code") String code);
    boolean existsByCode(String code);
    @Query("SELECT i FROM Expenses i WHERE i.date = :date")
    List<Expenses> history(@Param("date") Date date);
    @Query("SELECT SUM(e.amount) FROM Expenses e WHERE e.date = :date")
    Double total(@Param("date") Date date);
}
