package com.samplemission.collectcvsfromgoogledrive.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_applicant")
@EqualsAndHashCode(callSuper = true)
public class Applicant extends SoftDeletionEntity implements ResponsibleRelationship {
    @Type(type = "uuid-char")
    private UUID responsibleHrId;
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "surname", length = 100)
    private String surname;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "patronym", length = 100)
    private String patronym;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "salary")
    private BigInteger salary;

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

    @Column(name = "type_link", length = 50)
    private String typeLink;

    @Column(name = "work_permit")
    private Boolean workPermit;

    @Column(name = "is_viewed")
    private Boolean isViewed;

    @Column(name = "date_view")
    private Instant dateView;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "google_link", length = 100)
    private String googleLink;

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantContact_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantContact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantEducation_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantEducation> educations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantEmployment_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantEmployment> employments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantExperience_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantExperience> experiences = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantLanguage_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantLanguage> languages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = ApplicantSkill_.ID_APPLICANT, updatable = false, insertable = false)
    private List<ApplicantSkill> skills = new ArrayList<>();


}