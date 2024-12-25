package io.hhplus.architecture.domain.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
