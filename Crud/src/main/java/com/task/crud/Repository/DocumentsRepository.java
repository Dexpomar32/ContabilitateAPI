package com.task.crud.Repository;

import com.task.crud.Model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, String> {
    Documents findByCode(@Param("code") String code);
}
