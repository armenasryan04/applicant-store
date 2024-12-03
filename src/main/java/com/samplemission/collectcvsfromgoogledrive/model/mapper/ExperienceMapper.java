package com.samplemission.collectcvsfromgoogledrive.model.mapper;

import com.samplemission.collectcvsfromgoogledrive.model.dto.ExperienceDto;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantExperience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    ApplicantExperience toApplicantExperience(ExperienceDto experienceDto);
    ExperienceDto toExperienceDto(ApplicantExperience experience);
}
