package com.task.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Notes")
public class Notes {
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "note_code", nullable = false, unique = true)
    private String code;
    @Column(name = "note_text")
    private String text;
    @Column(name = "date")
    private Date date;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Projects project;
    @Transient
    private String projectCode;
}
