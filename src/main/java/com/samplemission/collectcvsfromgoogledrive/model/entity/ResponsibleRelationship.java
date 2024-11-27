package com.samplemission.collectcvsfromgoogledrive.model.entity;

import org.springframework.data.domain.Persistable;

import java.util.UUID;

public interface ResponsibleRelationship extends Persistable<UUID> {
    void setResponsibleHrId(UUID idResponsible);
}
