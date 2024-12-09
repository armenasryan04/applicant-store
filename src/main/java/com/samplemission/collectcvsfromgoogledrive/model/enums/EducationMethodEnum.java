package com.samplemission.collectcvsfromgoogledrive.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EducationMethodEnum {
    EDUCATION, COURSE, TEST, CERTIFICATE;

    private String getValue() {
        return this.name().toLowerCase();
    }
}
