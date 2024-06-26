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
@Table(name = "Income", indexes = {
        @Index(name = "Index_Income", columnList = "income_code")
})
public class Incomes {
    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "income_code", nullable = false, unique = true)
    private String code;
    @Column(name = "sale_date")
    private Date date;
    @Column(name = "amount")
    private Integer amount;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sale_id")
    private Sales sale;
    @Transient
    private String saleCode;
}
