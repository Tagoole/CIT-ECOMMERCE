package com.ecommerce.demo.features.notifications.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_owner_id", nullable = false)
    private User productOwner;

    @Column(nullable = false)
    private String text;
}
