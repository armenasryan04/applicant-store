package com.samplemission.collectcvsfromgoogledrive.model.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum DocumentTypeEnum {
    CV_HEAD_HUNTER("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword"),
    CV_LINKED_IN("application/pdf"),
    ATTACHMENT("application/pdf", "application/msword", "application/rtf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public static final String DOCX_MIME_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String DOC_MIME_TYPE = "application/msword";
    private final List<String> supportedMimeTypes;

    DocumentTypeEnum(String... supportedMimeTypes) {
        this.supportedMimeTypes = List.of(supportedMimeTypes);
    }

    public boolean isSupporting(String mimeType) {
        return supportedMimeTypes.contains(mimeType);
    }
}