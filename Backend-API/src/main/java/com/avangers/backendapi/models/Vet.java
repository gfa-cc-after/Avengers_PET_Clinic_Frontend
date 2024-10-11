package com.avangers.backendapi.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Entity
@Table(name = "vets")
@NoArgsConstructor
public class Vet extends User {
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clinic> clinics;
}
