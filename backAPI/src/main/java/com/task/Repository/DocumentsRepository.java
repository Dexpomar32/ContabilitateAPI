package com.task.Repository;

import com.task.Model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, String> {
    Documents findByCode(@Param("code") String code);
    boolean existsByCode(String code);
}
