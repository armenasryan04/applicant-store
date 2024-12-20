package com.samplemission.collectcvsfromgoogledrive.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeletionEntity extends AuditableEntity {
    @Column(name = "time_delete")
    private LocalDateTime timeDelete;

    public boolean isDeletedEntity() {
        return timeDelete != null;
    }
}