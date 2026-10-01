package com.winwintravel.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String passwordHash;
    @OneToOne(mappedBy = "user")
    private ProcessingLog processingLog;
}
