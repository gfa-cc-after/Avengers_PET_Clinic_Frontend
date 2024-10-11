package com.avangers.backendapi.models;

import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "admins")
@NoArgsConstructor
public class Admin extends User {

}
