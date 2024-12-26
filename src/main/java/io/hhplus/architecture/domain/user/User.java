package io.hhplus.architecture.domain.user;

import io.hhplus.architecture.domain.common.BaseAuditEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
