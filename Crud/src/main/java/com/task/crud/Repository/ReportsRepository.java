package com.task.crud.Repository;

import com.task.crud.Model.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, String> {
    Reports findByCode(@Param("code") String code);
}
