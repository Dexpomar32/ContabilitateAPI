package com.task.Repository;

import com.task.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, String> {
    Sales findByCode(@Param("code") String code);
    boolean existsByCode(String code);
    @Query("SELECT s FROM Sales s WHERE s.client.code = :code")
    List<Sales> history(@Param("code") String code);
}
