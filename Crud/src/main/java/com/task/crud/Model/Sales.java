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
@Table(name = "Sales")
public class Sales {
    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "sale_code")
    private String code;
    @Column(name = "sale_date")
    private Date date;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "client")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Clients client;
}