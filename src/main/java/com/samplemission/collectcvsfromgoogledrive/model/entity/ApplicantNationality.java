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
@Table(name = "tb_applicant_nationality")
public class ApplicantNationality extends AuditableEntity implements ApplicantRelationship {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;


}