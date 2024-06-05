package com.task.Service;

import com.task.Model.Deadline;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeadlineService {
    private final EntityManager entityManager;

    @Autowired
    public DeadlineService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Deadline> deadline() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("Deadline");
        Integer projectsDeadline = (Integer) storedProcedureQuery.getSingleResult();
        Deadline deadline = new Deadline(projectsDeadline);

        return Optional.of(deadline);
    }
}
