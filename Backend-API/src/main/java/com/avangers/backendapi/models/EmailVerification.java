package com.avangers.backendapi.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.util.UUID;

@Data
@Entity
@Table(name = "email_verifications")

@NoArgsConstructor
public class EmailVerification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String verificationId;
  private String email;
}
