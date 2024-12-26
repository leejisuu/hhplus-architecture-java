package io.hhplus.architecture.domain.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseAuditEntity extends BaseCreatedAtEntity {

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
