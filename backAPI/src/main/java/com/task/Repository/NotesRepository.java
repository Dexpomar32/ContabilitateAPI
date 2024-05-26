package com.task.Repository;

import com.task.Model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes, String> {
    Notes findByCode(@Param("code") String code);
}
