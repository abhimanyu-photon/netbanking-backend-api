package com.jpmc.netbanking.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    private String address;

    private Long phone_number;
}

