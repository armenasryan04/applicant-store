package com.samplemission.collectcvsfromgoogledrive.model.mapper;

import com.samplemission.collectcvsfromgoogledrive.model.dto.SkillDto;
import com.samplemission.collectcvsfromgoogledrive.model.dto.user.ResponsibleHrForRegistrationDto;
import com.samplemission.collectcvsfromgoogledrive.model.entity.Applicant;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantSkill;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ResponsibleHr;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

   ApplicantSkill toApplicantSkill(SkillDto skillDto);
   SkillDto toSkillDto (ApplicantSkill applicantSkill);
}