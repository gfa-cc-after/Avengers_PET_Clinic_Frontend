package com.avangers.backendapi.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @ManyToOne // many pets refer to one User {owner}
    @JoinColumn(name = "customer_id")
    private Customer owner;
}
