package com.task.Repository;

import com.task.Model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, String> {
    Clients findByCode(@Param("code") String code);
}
