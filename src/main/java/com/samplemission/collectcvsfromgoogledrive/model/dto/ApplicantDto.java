package com.samplemission.collectcvsfromgoogledrive.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDto {
    private String state;
    private String surname;
    private String name;
    private String patronym;
    private String gender;
    private BigInteger salary;
    private LocalDate dateBirth;
    private String currency;
    private String country;
    private String region;
    private String city;
    private String grade;
    private String position;
    private String relocation;
    private String isBusinessTrip;
    private String comment;
    private String typeLink;
    private Boolean workPermit;
    private Boolean isViewed;
    private LocalDateTime dateView;
    private Integer version;
    private NationalityDto nationality;
    private String googleLink;
    private List<ContactDto> contacts;
    private List<EducationDto> education;
    private List<LanguageDto> languages;
    private List<EmploymentDto> employments;
    private List<SkillDto> skills;
    private List<NationalityDto> nationalities;
    private List<ExperienceDto> experiences;
}
