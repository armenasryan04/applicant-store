package com.samplemission.collectcvsfromgoogledrive.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant")
public class Applicant {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private UUID id;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "surname", length = 100)
    private String surname;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "patronym", length = 100)
    private String patronym;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "currency", length = 100)
    private String currency;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "grade", length = 100)
    private String grade;

    @Column(name = "\"position\"", length = 100)
    private String position;

    @Column(name = "relocation", length = 100)
    private String relocation;

    @Column(name = "is_business_trip", length = 50)
    private String isBusinessTrip;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "date_update")
    private LocalDate dateUpdate;

    @Column(name = "type_link", length = 50)
    private String typeLink;

    @Column(name = "work_permit")
    private Boolean workPermit;

    @Column(name = "time_create", nullable = false)
    private Instant timeCreate;

    @Column(name = "time_update")
    private Instant timeUpdate;

    @Column(name = "time_delete")
    private Instant timeDelete;

    @Column(name = "is_viewed")
    private Boolean isViewed;

    @Column(name = "date_view")
    private Instant dateView;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "google_link", length = 100)
    private String googleLink;

}