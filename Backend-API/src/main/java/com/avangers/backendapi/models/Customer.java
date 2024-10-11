package com.avangers.backendapi.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    // orphanRemoval removes Pet from DB also once it is removed from List
    private List<Pet> pets = new ArrayList<>();

  @Column(name = "verified")
  private int verified;

  @Override
  public boolean isVerified() {
    return verified == 1;
  }

  public void setVerified(boolean verified) {
    this.verified = verified ? 1 : 0;
  }

  //Adds Pet to Customer
  public void addPet(Pet pet) {
    pets.add(pet);
    pet.setOwner(this);
  }

    // Remove Pet from Customer
    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setOwner(null);
    }
}
