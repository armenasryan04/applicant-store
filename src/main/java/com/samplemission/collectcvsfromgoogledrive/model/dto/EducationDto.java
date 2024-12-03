package com.samplemission.collectcvsfromgoogledrive.model.dto;

import lombok.Data;

@Data
public class EducationDto {
   private String educationType;
   private String universityName;
   private String specialization;
   private String organization;
   private String method;
   private Integer endYear;
}

