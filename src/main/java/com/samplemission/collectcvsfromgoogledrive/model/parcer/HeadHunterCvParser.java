package com.samplemission.collectcvsfromgoogledrive.model.parcer;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.samplemission.collectcvsfromgoogledrive.model.dto.ApplicantDto;
import com.samplemission.collectcvsfromgoogledrive.model.enums.DocumentTypeEnum;
import com.samplemission.collectcvsfromgoogledrive.utill.HhConversionUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.samplemission.collectcvsfromgoogledrive.model.enums.DocumentTypeEnum.CV_HEAD_HUNTER;
import static com.samplemission.collectcvsfromgoogledrive.model.enums.DocumentTypeEnum.DOCX_MIME_TYPE;


@Component
@AllArgsConstructor
public class HeadHunterCvParser implements CvParser<ApplicantDto> {

    public InformationFromCV informationFromCV;
    public HhConversionUtils hhConversionUtils;

    private static final String TIME_UPDATE_REGEX = "(\\d{2}:\\d{2})";
    private static final String DATE_UPDATE_REGEX = "(\\d+\\s+[а-я]+\\s+\\d{4})";
    private static final String DATE_TIME_PATTERN = "dd MMMM yyyy HH:mm";

    public void readGeneralInfo(XWPFTable table, ApplicantDto applicantDto) {
        XWPFTableRow generalInfoRow = table.getRow(0);
        XWPFTableCell cell = generalInfoRow.getTableCells().size() == 2
                ? generalInfoRow.getCell(1)
                : generalInfoRow.getCell(0);
        List<XWPFParagraph> xwpfParagraphs = cell.getParagraphs();
        String text = xwpfParagraphs.stream().map(XWPFParagraph::getText).collect(Collectors.joining("\n"));
        informationFromCV.fillGeneralInfo(applicantDto, text);
    }

    public void readPositionInfo(XWPFTable table, ApplicantDto applicantDto) {
        readInfo(table, applicantDto, "Желаемая должность и зарплата",
                informationFromCV::fillPositionInfo);
    }

    public void readExperienceInfo(XWPFTable table, ApplicantDto applicantDto) {
        readInfo(table, applicantDto, "Опыт работы",
               informationFromCV::fillExperienceInfo);
    }

    public void educationalInfo(XWPFTable table, ApplicantDto applicantDto) {
        List<XWPFTableRow> rows = table.getRows();
        Optional<XWPFTableRow> educationTitleRow = hhConversionUtils.findTitleRow(rows, "Образование");
        educationTitleRow.ifPresentOrElse((edu) -> {
            if (edu.getTableCells().size() > 1) {
                return;
            }
            List<XWPFTableRow> educationRows = hhConversionUtils.findRowsFromToTitleRows(rows, edu, null);
            informationFromCV.fillEducationInfo(applicantDto, educationRows);
        }, () -> System.out.println("Applicant CV without row - Образование, cant read education"));
    }

    public void readKeySkills(XWPFTable table, ApplicantDto applicantDto) {
        readInfo(table, applicantDto, "Ключевые навыки",
              informationFromCV::fillKeySkillsInfo);
    }

    private void readInfo(XWPFTable table,
                          ApplicantDto applicantDto,
                          String title,
                          BiConsumer<ApplicantDto, List<XWPFTableRow>> fillInfoMethod
    ) {
        List<XWPFTableRow> rows = table.getRows();
        Optional<XWPFTableRow> optionalTitleRow = hhConversionUtils.findTitleRow(rows, title);
        optionalTitleRow.ifPresentOrElse((titleRow) -> {
            XWPFTableRow nextTitleRow = hhConversionUtils.findNextTitleRow(rows, titleRow);
            List<XWPFTableRow> foundedRows = hhConversionUtils.findRowsBetween(rows, titleRow, nextTitleRow);
            fillInfoMethod.accept(applicantDto, foundedRows);
        }, () -> System.out.println("Applicant CV without row - " + title));
    }

    @Override
    public DocumentTypeEnum type() {
        return CV_HEAD_HUNTER;
    }

    @Override
    @SneakyThrows
    public ApplicantDto parseDocument(MultipartFile document) {
        InputStream inputStream;
        if (!Objects.equals(document.getContentType(), DOCX_MIME_TYPE)) {
            inputStream = new FileInputStream(convertToDocx(document));
        } else {
            inputStream = document.getInputStream();
        }
        try (inputStream) {
            return documentToDto(inputStream);
        }
    }

    @SneakyThrows
    private File convertToDocx(MultipartFile document) {
        File tempFile = Files.createTempFile("temp", ".docx").toFile();
        try (OutputStream os = new FileOutputStream(tempFile)) {
            Document doc = new Document(document.getInputStream());
            doc.save(os, SaveFormat.DOCX);
        }
        return tempFile;
    }

    @SneakyThrows
    private ApplicantDto documentToDto(InputStream inputStream) {
        XWPFDocument headHunterCv = new XWPFDocument(inputStream);
        ApplicantDto applicantDto = new ApplicantDto();
        if (!headHunterCv.getFooterList().isEmpty()) {
            applicantDto.setDateView(readTimeUpdate(headHunterCv.getFooterList().get(0).getText()));
        }
        XWPFTable table = headHunterCv.getTables().get(0);
        readGeneralInfo(table, applicantDto);
        readPositionInfo(table, applicantDto);
        readExperienceInfo(table, applicantDto);
        educationalInfo(table, applicantDto);
        readKeySkills(table, applicantDto);
        return applicantDto;
    }

    private LocalDateTime readTimeUpdate(String footer) {
        String foundDate = hhConversionUtils.findByRegex(DATE_UPDATE_REGEX, footer);
        String foundTime = hhConversionUtils.findByRegex(TIME_UPDATE_REGEX, footer);
        if (foundDate != null && foundTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, new Locale("ru", "RU"));
            String dateStr = foundDate + " " + foundTime;
            return LocalDateTime.parse(dateStr, formatter);
        }
        return null;
    }
}
