package com.samplemission.collectcvsfromgoogledrive.model.entity;

import javax.persistence.*;

import com.samplemission.collectcvsfromgoogledrive.model.enums.EducationMethodEnum;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_applicant_education")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ApplicantEducation extends AuditableEntity implements ApplicantRelationship {
    @Type(type="uuid-char")
    private UUID idApplicant;
    private String educationType;
    private String universityName;
    private String faculty;
    private String specialization;

    @Enumerated(EnumType.STRING)
    private EducationMethodEnum method;
    private String organization;
    private Integer endYear;
}