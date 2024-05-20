package com.task.crud.Repository;

import com.task.crud.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, String> {
    Sales findByCode(@Param("code") String code);
}
