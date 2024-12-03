package com.samplemission.collectcvsfromgoogledrive.model.mapper;

import com.samplemission.collectcvsfromgoogledrive.model.dto.EmploymentDto;
import com.samplemission.collectcvsfromgoogledrive.model.dto.user.ResponsibleHrForRegistrationDto;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantEmployment;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ResponsibleHr;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {

   EmploymentDto toEmploymentDto(ApplicantEmployment applicantEmployment);
   ApplicantEmployment toApplicantEmployment(EmploymentDto employmentDto);
}