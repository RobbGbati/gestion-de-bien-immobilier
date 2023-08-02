package com.gracetech.gestionimmoback.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditing implements Serializable {

    @CreatedBy
    @Column(name ="create_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "create_date", nullable = true, updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "update_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "update_date")
    private Instant lastModifiedDate = Instant.now();
}
