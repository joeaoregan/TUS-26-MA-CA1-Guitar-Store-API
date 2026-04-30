package edu.tus.guitarstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {

    /**
     * The @CreatedDate annotation is used to automatically populate
     * the createdAt field with the current date and time when a new
     * entity is persisted. The @Column(updatable = false) annotation
     * ensures that this field cannot be updated after it has been set.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The @CreatedBy annotation is used to automatically populate the
     * createdBy field with the name of the user who created the entity.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * The @LastModifiedDate annotation is used to automatically update
     * the updatedAt field with the current date and time whenever the
     * entity is updated. The @Column(insertable = false) annotation
     * ensures that this field cannot be set when the entity is first created.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /**
     * The @LastModifiedBy annotation is used to automatically populate the
     * updatedBy field with the name of the user who last modified the entity.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
