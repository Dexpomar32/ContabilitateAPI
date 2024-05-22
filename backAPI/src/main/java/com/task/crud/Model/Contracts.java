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
@Table(name = "Contracts")
public class Contracts {
    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "contract_code")
    private String code;
    @Column(name = "date")
    private Date date;
    @Column(name = "validity_period")
    private Date period;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Clients client;
    @Transient
    private String clientCode;
}
