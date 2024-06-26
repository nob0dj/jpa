package com.sh.app.student.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Department {
    COMPUTER_SCIENCE("CS"),
    BUSINESS_ADMINISTRATION("BA"),
    ECONOMICS("EC"),
    ENGLISH_LITERATURE("ENL"),
    KOREAN_LITERATURE("KRL");

    @Getter
    final String code;

}
