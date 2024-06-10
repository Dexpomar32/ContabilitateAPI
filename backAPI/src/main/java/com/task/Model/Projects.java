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
@Table(name = "Projects")
public class Projects {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "project_code", nullable = false, unique = true)
    private String code;
    @Column(name = "project_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Clients client;
    @Column(name = "completion_percentage")
    private Integer percentage;
    @Transient
    private String clientCode;
}
