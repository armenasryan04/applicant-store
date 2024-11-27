package com.samplemission.collectcvsfromgoogledrive.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant_experience")
public class ApplicantExperience {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_applicant", nullable = false)
    private Applicant idApplicant;

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

    @Column(name = "time_create", nullable = false)
    private Instant timeCreate;

    @Column(name = "time_update")
    private Instant timeUpdate;

}