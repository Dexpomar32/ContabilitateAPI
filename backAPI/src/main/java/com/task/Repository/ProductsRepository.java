package com.task.Repository;

import com.task.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {
    Products findByCode(@Param("code") String code);
    boolean existsByCode(String code);
}