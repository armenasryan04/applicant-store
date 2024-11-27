package com.samplemission.collectcvsfromgoogledrive.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_education")
public class ApplicantEducation {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_applicant", nullable = false)
    private Applicant idApplicant;

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

    @Column(name = "time_create", nullable = false)
    private Instant timeCreate;

    @Column(name = "time_update")
    private Instant timeUpdate;

}