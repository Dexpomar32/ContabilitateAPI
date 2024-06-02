package com.task.Repository;

import com.task.Model.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomesRepository extends JpaRepository<Incomes, String> {
    Incomes findByCode(@Param("code") String code);
}
