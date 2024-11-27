package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_employment")
public class ApplicantEmployment extends AuditableEntity implements ApplicantRelationship{
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "employment_type", nullable = false, length = 50)
    private String employmentType;

}