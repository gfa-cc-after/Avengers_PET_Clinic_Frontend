package com.avangers.backendapi.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clinics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

}
