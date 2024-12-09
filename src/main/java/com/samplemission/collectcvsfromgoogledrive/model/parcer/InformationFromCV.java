package com.samplemission.collectcvsfromgoogledrive.model.parcer;

import com.samplemission.collectcvsfromgoogledrive.model.dto.*;
import com.samplemission.collectcvsfromgoogledrive.model.enums.*;
import com.samplemission.collectcvsfromgoogledrive.utill.HhConversionUtils;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
public class InformationFromCV {

    private HhConversionUtils hhConversionUtils;
    private DateTimeFormatter monthYearPatternRu;

    @Autowired
    public InformationFromCV(HhConversionUtils hhConversionUtils, DateTimeFormatter monthYearPatternRu) {
        this.hhConversionUtils = hhConversionUtils;
        this.monthYearPatternRu = monthYearPatternRu;
    }

    public String nameSurnamePatronymRegex = "(.+(?=\\n(Женщина|Мужчина)))";
    public String genderRegex = "(Мужчина|Женщина)(.*родил(ся|ась)\\h(\\d{1,2}\\h[А-я]+\\h\\d{4}))?";
    public String cityRegex = "(?<=Проживает:\\h)([А-я-\\.\\h]+)";
    public String countryRegex = "(?<=Гражданство:\\h)([[А-я-\\.\\h]]+)";
    public String nationalityRegex = "(?<=Гражданство:\\s)(.*?)(?=\\s*,\\s*есть разрешение на работу|\\n)";
    public String relocationRegex = "(((Не\\hг|Г)отов)а?\\hк\\hпереезду|Хочу переехать)(:\\h([[А-я-\\.\\h]+,?]+))?,\\h((не\\hг|г)отов).*";
    public String workPermissionRegex = "(?<=(есть разрешение на работу: )).+";
    public String positionRegex = ".+(?=\\nСпециализации)";
    public String currencySalaryRegex = "(\\d+\\n|(\\d+ \\d+))\\n.+";
    public static final int SURNAME_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int PATRONYM_INDEX = 2;
    public static final int SIZE_WITHOUT_PATRONYM = 2;

    public static final int POSITION_ROW_INDEX = 0;
    public static final int POSITION_CELL_INDEX = 0;
    public static final int SALARY_CELL_INDEX = 1;
    public static final int ROW_SIZE_WITH_SALARY = 2;

    public String startDateRegex = "[а-яА-Я]+ \\d+(?= —)";
    public String endDateRegex = "(?<=— )[а-яА-Я]+ \\d+";
    public static final int DATE_CELL_INDEX = 0;
    public static final int MAIN_INFO_CELL_INDEX = 2;
    public static final int FIRST_DAY_OF_MONTH = 1;
    public static final int ORGANIZATION_NAME_INDEX = 0;
    public static final int POSITION_INDEX = 1;
    public static final double FONT_SIZE_PLAIN_TEXT = 9.0;
    private static final int CITY_INDEX = 0;
    private static final int SITE_INDEX = 1;
    private static final int FIRST_LETTER_INDEX = 0;

    public static final int INFO_CELL_INDEX = 1;
    public static final int EDUCATION_TYPE_ROW_INDEX = 0;
    public static final int FACULTY_AND_SPECIALIZATION_PARAGRAPH_INDEX = 1;
    public static final int FACULTY_INDEX = 0;
    public static final int SPECIALIZATION_INDEX = 1;
    public static final int MAIN_INFO_CELL_INDEX_SKILL = 1;
    public static final int TITLE_CELL_INDEX = 0;

    public void fillKeySkillsInfo(ApplicantDto applicantDto, List<XWPFTableRow> rows) {
        fillLanguageInfoIfPresent(applicantDto, rows);
        fillSkillsInfoIfPresent(applicantDto, rows);
    }

    private void fillLanguageInfoIfPresent(ApplicantDto applicantDto, List<XWPFTableRow> rows) {
        Optional<XWPFTableRow> languageRow = findRowByCellValue(rows, "Знание языков");
        languageRow.ifPresentOrElse(
                lan -> {
                    List<XWPFParagraph> languageParagraphs = lan.getCell(MAIN_INFO_CELL_INDEX_SKILL).getParagraphs();
                    fillLanguageInfo(applicantDto, languageParagraphs);
                },
                () -> System.out.println("error")
        );
    }

    private void fillSkillsInfoIfPresent(ApplicantDto applicantDto, List<XWPFTableRow> rows) {
        Optional<XWPFTableRow> skillsRow = findRowByCellValue(rows, "Навыки");
        skillsRow.ifPresentOrElse(
                skill -> {
                    List<XWPFParagraph> skillsParagraphs = skill.getCell(MAIN_INFO_CELL_INDEX_SKILL).getParagraphs();
                    fillSkillsInfo(applicantDto, skillsParagraphs);
                },
                () -> System.out.println("error")
        );
    }

    private Optional<XWPFTableRow> findRowByCellValue(List<XWPFTableRow> rows, String cellValue) {
        return rows.stream()
                .filter(row -> row.getCell(TITLE_CELL_INDEX).getText().equals(cellValue))
                .findFirst();
    }

    public void fillLanguageInfo(ApplicantDto applicantDto, List<XWPFParagraph> paragraphs) {
        List<LanguageDto> languageDtos = paragraphs.stream()
                .map(XWPFParagraph::getText) // Получаем текст параграфа
                .filter(text -> text.contains("—")) // Проверяем, что есть разделитель "—"
                .map(text -> {
                    // Разделяем текст по "—"
                    String[] parts = text.split("—");
                    String language = parts[0].trim(); // Язык
                    String level = parts.length > 1 ? parts[1].trim() : "Не указан"; // Уровень владения
                    String attribute = parts.length > 2 ? parts[2].trim() : level; // Описание (если есть)
                    return new LanguageDto(attribute, language, level);
                })
                .toList();

        applicantDto.setLanguages(languageDtos); // Устанавливаем данные в DTO
    }


    public void fillSkillsInfo(ApplicantDto applicantDto, List<XWPFParagraph> paragraphs) {
        // Список навыков
        List<SkillDto> skillDtoList = paragraphs.stream()
                .map(XWPFParagraph::getText) // Получаем текст каждого параграфа
                .flatMap(text -> Arrays.stream(text.split("\\s{2,}"))) // Разделяем текст по двум и более пробелам (визуальные разрывы)
                .map(String::trim) // Убираем лишние пробелы
                .filter(skill -> !skill.isEmpty()) // Убираем пустые строки
                .map(skill -> new SkillDto(skill)) // Создаём SkillDto для каждого навыка
                .toList();

        // Устанавливаем список навыков в ApplicantDto
        applicantDto.setSkills(skillDtoList);
    }



    public void fillEducationInfo(ApplicantDto applicantDto, List<XWPFTableRow> rows) {
        List<EducationDto> educationDtos = new ArrayList<>();
        educationDtos.addAll(fillEducation(rows));
        educationDtos.addAll(fillSection(rows, "Повышение квалификации, курсы", EducationMethodEnum.COURSE));
        educationDtos.addAll(fillSection(rows, "Тесты, экзамены", EducationMethodEnum.TEST));
        educationDtos.addAll(fillCertificate(rows));
        applicantDto.setEducation(educationDtos);
    }

    private List<EducationDto> fillEducation(List<XWPFTableRow> rows) {
        List<XWPFTableRow> foundedRows = hhConversionUtils.findRowsUpToNextTitle(rows, "Образование");
        XWPFTableRow levelRow = foundedRows.get(EDUCATION_TYPE_ROW_INDEX);
        if (levelRow.getCell(DATE_CELL_INDEX).getText().contains("Уровень")) {
            var applicantEducationDto = new EducationDto();
            applicantEducationDto.setMethod(EducationMethodEnum.EDUCATION);
            applicantEducationDto.setEducationType(getEducationType(levelRow.getCell(INFO_CELL_INDEX).getText()));
            return List.of(applicantEducationDto);
        }
        String educationType = getEducationType(levelRow.getCell(DATE_CELL_INDEX).getText());
        List<XWPFTableRow> educationRows = hhConversionUtils.findRowsBetween(foundedRows, levelRow, null);
        return educationRows.stream()
                .map(row -> createEducationDto(row, EducationMethodEnum.EDUCATION))
                .peek(dto -> dto.setEducationType(educationType))
                .toList();
    }

    private List<EducationDto> fillCertificate(List<XWPFTableRow> rows) {
        List<XWPFTableRow> foundedRows = hhConversionUtils.findRowsUpToNextTitle(rows, "Электронные сертификаты");
        List<EducationDto> certificateDtos = new ArrayList<>();
        for (XWPFTableRow row : foundedRows) {
            certificateDtos.addAll(getCertificatesFromRow(row));
        }
        return certificateDtos;
    }

    private List<EducationDto> getCertificatesFromRow(XWPFTableRow row) {
        Integer endYear = Integer.valueOf(row.getCell(DATE_CELL_INDEX).getText().trim());
        String[] certificates = row.getCell(INFO_CELL_INDEX).getText().split("\n");
        return Arrays.stream(certificates)
                .map(certificate -> createCertificateEducationDto(endYear, certificate))
                .collect(Collectors.toList());
    }

    private EducationDto createCertificateEducationDto(Integer date, String specialization) {
        var applicantEducationDto = new EducationDto();
        applicantEducationDto.setMethod(EducationMethodEnum.CERTIFICATE);
        applicantEducationDto.setSpecialization(specialization);
        applicantEducationDto.setEndYear(date);
        return applicantEducationDto;
    }

    private List<EducationDto> fillSection(List<XWPFTableRow> rows, String sectionTitle, EducationMethodEnum method) {
        List<XWPFTableRow> foundedRows = hhConversionUtils.findRowsUpToNextTitle(rows, sectionTitle);
        return fillEducationDtoList(foundedRows, method);
    }

    private List<EducationDto> fillEducationDtoList(List<XWPFTableRow> rows, EducationMethodEnum method) {
        return rows.stream()
                .filter(row -> row.getTableCells().size() > 1)
                .map(row -> createEducationDto(row, method))
                .toList();
    }

    private String getEducationType(String level) {
        return Arrays.stream(EducationLevelEnum.values())
                .filter(educationLevel -> level.toLowerCase().contains(educationLevel.getDescription()))
                .findFirst()
                .map(EducationLevelEnum::getValue)
                .orElse(null);
    }

    private EducationDto createEducationDto(XWPFTableRow row, EducationMethodEnum method) {
        EducationDto applicantEducationDto = new EducationDto();
        XWPFTableCell infoCell = row.getCell(INFO_CELL_INDEX);
        applicantEducationDto.setMethod(method);
        setOtherField(applicantEducationDto, infoCell);
        setEndYear(applicantEducationDto, row.getCell(DATE_CELL_INDEX));
        return applicantEducationDto;
    }

    private String findUniversityName(XWPFTableCell infoCell) {
        return infoCell.getParagraphs().stream()
                .filter(paragraph -> paragraph.getRuns().stream()
                        .allMatch(XWPFRun::isBold))
                .map(XWPFParagraph::getText)
                .findFirst()
                .orElse(null);
    }

    private void setOtherField(EducationDto applicantEducationDto, XWPFTableCell infoCell) {
        applicantEducationDto.setUniversityName(findUniversityName(infoCell));
        if (infoCell.getParagraphs().size() > 1) {
            List<String> facultyAndSpecialization = hhConversionUtils.formatFacultyAndSpecialization(infoCell.getParagraphs().get(FACULTY_AND_SPECIALIZATION_PARAGRAPH_INDEX).getText());
            if (applicantEducationDto.getMethod() == EducationMethodEnum.EDUCATION) {
                applicantEducationDto.setFaculty(facultyAndSpecialization.get(FACULTY_INDEX));
            } else {
                applicantEducationDto.setOrganization(facultyAndSpecialization.get(FACULTY_INDEX));
            }
            applicantEducationDto.setSpecialization(facultyAndSpecialization.get(SPECIALIZATION_INDEX));
        }
    }

    private void setEndYear(EducationDto applicantEducationDto, XWPFTableCell dateCell) {
        if (!dateCell.getText().isBlank()) {
            applicantEducationDto.setEndYear(Integer.valueOf(dateCell.getText()));
        }
    }

    public void fillExperienceInfo(ApplicantDto applicantDto, List<XWPFTableRow> experienceRows) {
        List<ExperienceDto> applicantExperienceDtos = experienceRows.stream().filter(row -> row.getTableCells().size() > 1).map(row -> {
            String dateTexts = row.getCell(DATE_CELL_INDEX).getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining());
            List<XWPFParagraph> mainInfoParagraphs = row.getCell(MAIN_INFO_CELL_INDEX).getParagraphs();
            ExperienceDto applicantExperienceDto = new ExperienceDto();
            fillExperienceDates(applicantExperienceDto, dateTexts);
            fillExperienceMainInfo(applicantExperienceDto, mainInfoParagraphs);
            return applicantExperienceDto;
        }).toList();
        applicantDto.setExperiences(applicantExperienceDtos);
    }

    public void fillExperienceDates(ExperienceDto applicantExperienceDto, String text) {
        applicantExperienceDto.setStartDate(getDateByRegex(text, startDateRegex));
        applicantExperienceDto.setEndDate(getDateByRegex(text, endDateRegex));
    }

    private LocalDate getDateByRegex(String text, String regex) {
        String dateTime = hhConversionUtils.findByRegex(regex, text);
        if (dateTime != null) {
            YearMonth dateYearMonth = YearMonth.parse(dateTime.toLowerCase(), monthYearPatternRu);
            return dateYearMonth.atDay(FIRST_DAY_OF_MONTH);
        }
        return null;
    }

    @SneakyThrows
    public void fillExperienceMainInfo(ExperienceDto applicantExperienceDto, List<XWPFParagraph> mainInfoParagraphs) {
        List<XWPFParagraph> nameAndPositionParagraphs = getNameAndPositionParagraphs(mainInfoParagraphs);
        XWPFParagraph organisationName = nameAndPositionParagraphs.get(ORGANIZATION_NAME_INDEX);
        XWPFParagraph position = nameAndPositionParagraphs.get(POSITION_INDEX);
        applicantExperienceDto.setCompanyName(organisationName.getText());
        applicantExperienceDto.setSite(getSite(mainInfoParagraphs));
        applicantExperienceDto.setPosition(position.getText());
        applicantExperienceDto.setDescription(getDescription(mainInfoParagraphs, organisationName, position));
        applicantExperienceDto.setActivity(getActivity(mainInfoParagraphs, position));
    }

    private String getSite(List<XWPFParagraph> mainInfoParagraphs) {
        String cityAndSite = getCityAndSite(mainInfoParagraphs);
        if (cityAndSite != null) {
            String[] parts = cityAndSite.split(", ");
            if (parts.length == 2) {
                return parts[SITE_INDEX];
            }
            if (parts.length == 1 && Character.isLowerCase(parts[CITY_INDEX].charAt(FIRST_LETTER_INDEX))) {
                return parts[CITY_INDEX];
            }
        }
        return null;
    }

    private String getCityAndSite(List<XWPFParagraph> mainInfoParagraphs) {
        return mainInfoParagraphs.stream()
                .filter(xwpfParagraph -> xwpfParagraph.getRuns()
                        .stream()
                        .anyMatch(run -> Objects.equals(run.getColor(), "AEAEAE")))
                .findFirst()
                .map(XWPFParagraph::getText)
                .orElse(null);
    }

    private List<XWPFParagraph> getNameAndPositionParagraphs(List<XWPFParagraph> mainInfoParagraphs) {
        return mainInfoParagraphs.stream()
                .filter(xwpfParagraph -> xwpfParagraph.getRuns()
                        .stream()
                        .anyMatch(run -> run.getFontSizeAsDouble() == null || run.getFontSizeAsDouble() != FONT_SIZE_PLAIN_TEXT))
                .toList();
    }

    private String getActivity(List<XWPFParagraph> mainInfoParagraphs, XWPFParagraph position) {
        List<XWPFParagraph> activityParagraphs = hhConversionUtils.findParagraphsBetween(mainInfoParagraphs, position, null);
        activityParagraphs.forEach(paragraph -> System.out.println("Activity paragraph text: " + paragraph.getText()));
        return activityParagraphs.stream()
                .map(XWPFParagraph::getText)
                .map(text -> text.replace("\n", "\n\n"))  // Можно заменить на одинарные \n, если требуется
                .collect(Collectors.joining("\n"));
    }


    private String getDescription(List<XWPFParagraph> mainInfoParagraphs, XWPFParagraph organisationName, XWPFParagraph position) {
        List<XWPFParagraph> descriptionParagraphs = hhConversionUtils.findParagraphsFromTo(mainInfoParagraphs, organisationName, position);
        return descriptionParagraphs.stream()
                .filter(paragraph -> !Objects.equals(paragraph, organisationName))
                .filter(paragraph -> paragraph.getRuns()
                        .stream()
                        .noneMatch(run -> Objects.equals(run.getColor(), "AEAEAE")))
                .map(XWPFParagraph::getText)
                .map(text -> text.replace("•", "—"))
                .collect(Collectors.joining("\n\n"));
    }


    public void fillPositionInfo(ApplicantDto applicantDto, List<XWPFTableRow> positionRows) {
        XWPFTableRow positionRow = positionRows.get(POSITION_ROW_INDEX);
        fillPosition(applicantDto, positionRow.getCell(POSITION_CELL_INDEX).getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining("\n")));
        fillEmployments(applicantDto, positionRow.getCell(POSITION_CELL_INDEX).getParagraphs());
        if (positionRow.getTableCells().size() == ROW_SIZE_WITH_SALARY) {
            fillSalaryAndCurrency(applicantDto, positionRow.getCell(SALARY_CELL_INDEX).getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining("\n")));
        }
    }

    public void fillPosition(ApplicantDto applicantDto, String text) {
        String position = hhConversionUtils.findByRegex(positionRegex, text);
        applicantDto.setPosition(position);
        applicantDto.setGrade(null);
    }

    private void fillEmployments(ApplicantDto applicantDto, List<XWPFParagraph> paragraphs) {
        Optional<XWPFParagraph> employmentParagraph = paragraphs.stream()
                .filter(paragraph -> paragraph.getText().startsWith("Занятость:"))
                .findFirst();

        employmentParagraph.ifPresent(paragraph -> {
            String employmentText = paragraph.getText().replaceFirst("Занятость:", "").trim();
            String[] employmentItems = employmentText.split(",");

            List<EmploymentDto> employments = Arrays.stream(employmentItems)
                    .map(String::trim)
                    .map(employmentType -> {
                        EmploymentDto dto = new EmploymentDto();
                        dto.setEmploymentType(employmentType);
                        return dto;
                    })
                    .toList();

            applicantDto.setEmployments(employments);
        });

        if (employmentParagraph.isEmpty()) {

        }
    }


    public void fillSalaryAndCurrency(ApplicantDto applicantDto, String text) {
        String salaryCurrency = hhConversionUtils.findByRegex(currencySalaryRegex, text);
        if (salaryCurrency == null) {
            return;
        }
        String salary = hhConversionUtils.findByRegex("(\\d+\\n|(\\d+ \\d+))", salaryCurrency);
        String currency = hhConversionUtils.findByRegex("(?<=\\n).+", salaryCurrency);
        salary = salary.replace(" ", "");
        applicantDto.setCurrency(currency);
        applicantDto.setSalary(new BigInteger(salary));
    }

    public void fillGeneralInfo(ApplicantDto applicantDto, String text) {
        fillNameSurnamePatronym(applicantDto, text);
        fillCountry(applicantDto, text);
        fillCity(applicantDto, text);
        fillRelocation(applicantDto, text);
        fillBioInfo(applicantDto, text);
        fillContacts(applicantDto, text);
        fillWorkPermission(applicantDto, text);
        fillNationality(applicantDto, text);
    }

    public void fillNameSurnamePatronym(ApplicantDto applicantDto, String text) {
        String nameSurnamePatronym = hhConversionUtils.findByRegex(nameSurnamePatronymRegex, text);
        String[] parts = nameSurnamePatronym.split("\\s");
        applicantDto.setSurname(parts[SURNAME_INDEX]);
        applicantDto.setName(parts[NAME_INDEX]);
        if (parts.length > SIZE_WITHOUT_PATRONYM) {
            applicantDto.setPatronym(parts[PATRONYM_INDEX]);
        }
    }

    public void fillCountry(ApplicantDto applicantDto, String text) {
        String region = hhConversionUtils.findByRegex(countryRegex, text);
        applicantDto.setCountry(region);

    }
    public void fillNationality(ApplicantDto applicantDto, String text) {
        String nationality = hhConversionUtils.findByRegex(nationalityRegex, text);

        // Проверяем, найдено ли гражданство в тексте
        if (nationality != null && !nationality.isEmpty()) {
            // Устанавливаем найденное гражданство как национальность
            applicantDto.setNationalities(Arrays.asList(new NationalityDto(nationality)));
        } else {
            // Если гражданство не найдено
            System.out.println("Не удалось извлечь гражданство из текста.");
        }
    }
    public void fillCity(ApplicantDto applicantDto, String text) {
        String city = hhConversionUtils.findByRegex(cityRegex, text);
        applicantDto.setCity(city);
    }

    public void fillRelocation(ApplicantDto applicantDto, String text) {
        String relocationAndBusinessTrip = hhConversionUtils.findByRegex(relocationRegex, text);
        applicantDto.setRelocation(getRelocation(relocationAndBusinessTrip));
        applicantDto.setIsBusinessTrip(getBusinessTrip(relocationAndBusinessTrip).getCorrectDescription());
    }

    private static BusinessTripTypeEnum getBusinessTrip(String relocationAndBusinessTrip) {
        return Arrays.stream(BusinessTripTypeEnum.values())
                .filter(e -> relocationAndBusinessTrip.matches(e.getDescription()))
                .findFirst()
                .orElse(null);
    }

    private String getRelocation(String text) {
        return Arrays.stream(RelocationEnum.values())
                .filter(relocationEnum -> hhConversionUtils.checkIfContainsRegex(relocationEnum.getRegex(), text))
                .findFirst()
                .map(RelocationEnum::getValue)
                .orElseGet(() -> {
                    return null;
                });
    }

    public void fillBioInfo(ApplicantDto applicantDto, String text) {
        String bioInfo = hhConversionUtils.findByRegex(genderRegex, text);
        String gender = hhConversionUtils.findByRegex("(Мужчина|Женщина)", bioInfo);
        String dateOfBirthday = hhConversionUtils.findByRegex("\\d+ [а-яА-Я]+ \\d+", bioInfo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("ru"));

        if (dateOfBirthday != null) {
            LocalDate ld = LocalDate.parse(dateOfBirthday, formatter);
            applicantDto.setDateBirth(ld);
        }
        applicantDto.setGender(gender);
    }

    public void fillContacts(ApplicantDto applicantDto, String text) {
        List<String> probableContacts = List.of(text.split("\n"));
        if (probableContacts.isEmpty()) {
            return;
        }
        applicantDto.setContacts(
                Arrays.stream(ContactTypeEnum.values())
                        .flatMap(type -> probableContacts.stream()
                                .filter(contact -> hhConversionUtils.checkIfContainsRegex(type.getRegex(), contact))
                                .map(contact -> getApplicantContactDto(type.getValue(), hhConversionUtils.findByRegex(type.getRegex(), contact))))
                        .toList());
    }

    private ContactDto getApplicantContactDto(String type, String value) {
        var contact = new ContactDto();
        contact.setContactType(type);
        if (Objects.equals(type, ContactTypeEnum.MOBILE_PHONE.getValue())) {
            value = value.replaceAll("[^\\d+.]", "");
        }
        contact.setValue(value);
        return contact;
    }


    public void fillWorkPermission(ApplicantDto applicantDto, String text) {
        String workPermission = hhConversionUtils.findByRegex(workPermissionRegex, text);
        if (workPermission != null && workPermission.contains("Россия")) {
            applicantDto.setWorkPermit(true);
        }
    }

}
