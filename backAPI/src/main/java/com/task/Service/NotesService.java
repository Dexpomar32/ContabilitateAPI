package com.task.Service;

import com.task.DTO.Mapper.NotesMapper;
import com.task.DTO.Records.NotesRecord;
import com.task.Model.Notes;
import com.task.Model.Projects;
import com.task.Repository.NotesRepository;
import com.task.Repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NotesService {
    private final NotesRepository notesRepository;
    private final NotesMapper notesMapper;
    private final ProjectsRepository projectsRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository, NotesMapper notesMapper, ProjectsRepository projectsRepository) {
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
        this.projectsRepository = projectsRepository;
    }

    public Optional<List<NotesRecord>> getAll() {
        List<NotesRecord> notesRecordList = notesRepository
                .findAll()
                .stream()
                .map(notesMapper)
                .toList();

        return Optional.of(notesRecordList);
    }

    public Optional<NotesRecord> getOne(String code) {
        return Optional.ofNullable(notesRepository.findByCode(code))
                .map(notesMapper);
    }

    public Optional<NotesRecord> create(Notes note) {
        if (check(note)) {
            return Optional.empty();
        }

        if (note.getCode() == null || note.getCode().isEmpty()) {
            note.setCode(generateUniqueCode());
        }

        Projects projects = projectsRepository.findByCode(note.getProjectCode());
        note.setProject(projects);
        notesRepository.save(note);
        return Optional.ofNullable(notesMapper.apply(note));
    }

    public Optional<NotesRecord> update(Notes note) {
        if (check(note)) {
            return Optional.empty();
        }

        Projects projects = projectsRepository.findByCode(note.getProjectCode());
        note.setProject(projects);

        return Optional.ofNullable(notesRepository.findByCode(note.getCode()))
                .map(existingNote -> {
                    existingNote.setText(note.getText());
                    existingNote.setDate(note.getDate());
                    existingNote.setProject(note.getProject());
                    notesRepository.save(existingNote);
                    return notesMapper.apply(existingNote);
                });
    }

    public Optional<NotesRecord> delete(String code) {
        Optional<Notes> optionalExpense = Optional.ofNullable(notesRepository.findByCode(code));

        return optionalExpense.map(note -> {
            optionalExpense.ifPresent(notesRepository::delete);
            return notesMapper.apply(note);
        });
    }

    public boolean check(Notes note) {
        return Stream.of(note.getText(), note.getDate(), note.getProjectCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode();
        } while (notesRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}