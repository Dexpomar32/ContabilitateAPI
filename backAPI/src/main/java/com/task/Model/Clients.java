package com.task.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Clients")
public class Clients {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NaturalId
    @Column(name = "client_code", nullable = false, unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private Long number;
}