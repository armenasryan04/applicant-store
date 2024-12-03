package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_education")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ApplicantEducation extends AuditableEntity implements ApplicantRelationship{

    @Type(type="uuid-char")
    private UUID idApplicant;

    @Column(name = "education_type", length = 50)
    private String educationType;

    @Column(name = "university_name", length = 500)
    private String universityName;

    @Column(name = "faculty", length = 500)
    private String faculty;

    @Column(name = "specialization", length = 500)
    private String specialization;

    @Column(name = "organization", length = 500)
    private String organization;

    @Column(name = "method", length = 50)
    private String method;

    @Column(name = "end_year")
    private Integer endYear;

}