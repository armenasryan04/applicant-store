package com.samplemission.collectcvsfromgoogledrive.endpoint;

import com.samplemission.collectcvsfromgoogledrive.model.dto.ApplicantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileParserController {

    @GetMapping("/parse-files")
    public ApplicantDto parseFiles() throws Exception {
        ApplicantDto applicantDto = new ApplicantDto();
        return applicantDto;
    }
}
