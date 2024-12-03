package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_skill")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ApplicantSkill extends AuditableEntity implements ApplicantRelationship{

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "skill_name", nullable = false, length = 250)
    private String skillName;


}