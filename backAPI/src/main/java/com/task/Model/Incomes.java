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
@Table(name = "Income")
public class Incomes {
    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "income_code")
    private String code;
    @Column(name = "sale_date")
    private Date date;
    @Column(name = "amount")
    private Double amount;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sale_id")
    private Sales sale;
    @Transient
    private String saleCode;
}
