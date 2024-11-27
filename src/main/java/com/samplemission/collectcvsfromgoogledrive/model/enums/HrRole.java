package com.samplemission.collectcvsfromgoogledrive.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HrRole {
    ADMIN("Админ"),
    USER("Ползователь");
    private final String roleDescription;
}
