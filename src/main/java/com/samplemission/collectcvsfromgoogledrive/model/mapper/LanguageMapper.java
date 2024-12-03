package com.samplemission.collectcvsfromgoogledrive.model.mapper;

import com.samplemission.collectcvsfromgoogledrive.model.dto.LanguageDto;
import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantLanguage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    ApplicantLanguage toApplicantLanguage(LanguageDto languageDto);
    LanguageDto toLanguageDto(ApplicantLanguage applicantLanguage);
}
