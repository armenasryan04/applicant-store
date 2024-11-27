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
@Table(name = "tb_applicant_nationality")
public class ApplicantNationality {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_applicant", nullable = false)
    private Applicant idApplicant;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;

    @Column(name = "time_create", nullable = false)
    private Instant timeCreate;

    @Column(name = "time_update")
    private Instant timeUpdate;

}