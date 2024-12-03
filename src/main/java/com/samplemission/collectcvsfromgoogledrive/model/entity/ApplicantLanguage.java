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
@Table(name = "tb_applicant_language")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ApplicantLanguage extends AuditableEntity implements ApplicantRelationship {

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "attribute", length = 50)
    private String attribute;

    @Column(name = "language", nullable = false, length = 50)
    private String language;

    @Column(name = "level", nullable = false, length = 50)
    private String level;


}