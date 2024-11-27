package com.samplemission.collectcvsfromgoogledrive.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tb_applicant_contact")
public class ApplicantContact extends AuditableEntity implements ApplicantRelationship{

    @Type(type="uuid-char")
    private UUID idApplicant;
    private String contactType;
    @Column(name = "`value`", nullable = false, length = 100)
    private String value;

}