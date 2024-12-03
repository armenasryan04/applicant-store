package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_nationality")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ApplicantNationality extends AuditableEntity implements ApplicantRelationship {

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;


}