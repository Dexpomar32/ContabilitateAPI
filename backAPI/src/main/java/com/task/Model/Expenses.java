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
@Table(name = "Expenses")
public class Expenses {
    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "expense_code", nullable = false, unique = true)
    private String code;
    @Column(name = "date")
    private Date date;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "description")
    private String description;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "project_id")
    private Projects project;
    @Transient
    private String projectCode;
}
