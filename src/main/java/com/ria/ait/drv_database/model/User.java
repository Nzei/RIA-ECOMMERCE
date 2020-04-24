package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String county;
    private String phone;
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
