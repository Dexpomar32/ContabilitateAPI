package com.task.Repository;

import com.task.Model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, String> {
    Projects findByCode(@Param("code") String code);
}
