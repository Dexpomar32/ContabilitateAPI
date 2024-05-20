package com.task.crud.Service;

import com.task.crud.DTO.Mapper.NotesMapper;
import com.task.crud.DTO.Records.NotesRecord;
import com.task.crud.Model.Notes;
import com.task.crud.Repository.NotesRepository;
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

    @Autowired
    public NotesService(NotesRepository notesRepository, NotesMapper notesMapper) {
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
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

        notesRepository.save(note);
        return Optional.ofNullable(notesMapper.apply(note));
    }

    public Optional<NotesRecord> update(Notes note) {
        if (check(note)) {
            return Optional.empty();
        }

        return Optional.ofNullable(notesRepository.findByCode(note.getCode()))
                .map(existingNote -> {
                    existingNote.setText(note.getText());
                    existingNote.setDate(note.getDate());
                    existingNote.getProject().setCode(note.getCode());
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
        return Stream.of(note.getCode(), note.getText(), note.getDate(), note.getProject().getCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
