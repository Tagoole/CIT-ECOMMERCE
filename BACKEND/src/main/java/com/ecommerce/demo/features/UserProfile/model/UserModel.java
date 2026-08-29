package com.ecommerce.demo.features.UserProfile.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String username;

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 10,nullable = false)
    private String phoneNumber;

    @Column(length = 100,nullable = false)
    private String email;
}
