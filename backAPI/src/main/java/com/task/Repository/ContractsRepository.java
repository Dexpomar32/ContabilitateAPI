package com.task.Repository;

import com.task.Model.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts, String> {
    Contracts findByCode(@Param("code") String code);
}
