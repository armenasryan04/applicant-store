package com.samplemission.collectcvsfromgoogledrive.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
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
    private BigDecimal salary;
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
    private Instant dateView;
    private Integer version;
    private NationalityDto nationality;
    private String googleLink;
    private List<ContactDto> contacts;
    private List<EducationDto> educations;
    private List<EmploymentDto> employments;
    private List<ExperienceDto> experiences;
}
