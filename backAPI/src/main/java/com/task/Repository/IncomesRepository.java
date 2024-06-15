package com.task.Repository;

import com.task.Model.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IncomesRepository extends JpaRepository<Incomes, String> {
    Incomes findByCode(@Param("code") String code);
    boolean existsByCode(String code);
    @Query("SELECT i FROM Incomes i WHERE i.date = :date")
    List<Incomes> history(@Param("date") Date date);
    @Query("SELECT SUM(i.amount) FROM Incomes i WHERE i.date = :date")
    Double total(@Param("date") Date date);
}
