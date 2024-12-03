package com.samplemission.collectcvsfromgoogledrive.model.mapper;

import com.samplemission.collectcvsfromgoogledrive.model.dto.ApplicantDto;
import com.samplemission.collectcvsfromgoogledrive.model.dto.user.ResponsibleHrForRegistrationDto;
import com.samplemission.collectcvsfromgoogledrive.model.entity.Applicant;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ResponsibleHr;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {

    ApplicantDto toApplicantDto(Applicant applicant);

    Applicant toApplicant(ApplicantDto applicantDto);
}