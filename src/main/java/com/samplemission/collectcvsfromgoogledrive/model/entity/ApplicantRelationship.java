package com.samplemission.collectcvsfromgoogledrive.model.entity;

import org.springframework.data.domain.Persistable;

import java.util.UUID;

public interface ApplicantRelationship extends Persistable<UUID> {
    void setIdApplicant(UUID idApplicant);
}
