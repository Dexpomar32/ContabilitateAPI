package com.task.crud.Model;

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
@Table(name = "Reports")
public class Reports {
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "report_code")
    private String code;
    @Column(name = "report_date")
    private Date date;
    @Column(name = "report_text")
    private String text;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "project_id")
    private Projects project;
    @Transient
    private String projectCode;
}
