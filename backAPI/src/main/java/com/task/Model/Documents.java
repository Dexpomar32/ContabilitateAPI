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
@Table(name = "Documents")
public class Documents {
    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "document_code", nullable = false, unique = true)
    private String code;
    @Column(name = "document_type")
    private String type;
    @Column(name = "date")
    private Date date;
    @Column(name = "text")
    private String text;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Clients client;
    @Transient
    private String clientCode;
}
