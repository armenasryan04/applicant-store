package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_experience")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ApplicantExperience extends AuditableEntity implements ApplicantRelationship {

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "company_name", nullable = false, length = 250)
    private String companyName;

    @Column(name = "site", length = 100)
    private String site;

    @Column(name = "activity", length = Integer.MAX_VALUE)
    private String activity;

    @Column(name = "\"position\"", length = 250)
    private String position;

    @Column(name = "description", length = 2500)
    private String description;

}