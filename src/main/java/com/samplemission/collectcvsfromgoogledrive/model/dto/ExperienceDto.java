package com.samplemission.collectcvsfromgoogledrive.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String companyName;
    private String site;
    private String activity;
    private String position;
    private String description;
}
