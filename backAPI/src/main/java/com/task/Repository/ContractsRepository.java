package com.task.Repository;

import com.task.Model.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts, String> {
    Contracts findByCode(@Param("code") String code);
    boolean existsByCode(String code);
    @Query("SELECT c FROM Contracts c WHERE c.client.code = :code")
    Contracts findByClientCode(@Param("code") String code);
}
