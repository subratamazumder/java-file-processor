package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.service.formatter.DataFormatter;
import com.subrata.poc.validator.CustomValidator;

import java.time.format.DateTimeFormatter;

public class DOBFormatter implements DataFormatter {
    final private String RAW_DATE_FORMAT = "yyyyMMdd";
    final private String PRETTY_DATE_FORMAT = "dd/MM/yy";
    final DateTimeFormatter RAW_DATE_FORMATTER = DateTimeFormatter.ofPattern(RAW_DATE_FORMAT);
    final DateTimeFormatter PRETTY_DATE_FORMATTER = DateTimeFormatter.ofPattern(PRETTY_DATE_FORMAT);
    private final boolean isRaw;
    private final CustomValidator dobValidator;
    @Override
    public String format(String rawDob) {
        //optional field can be empty
        String EMPTY_STRING = "";
        if(rawDob.trim().isEmpty()){
            return EMPTY_STRING;
        }
        if(dobValidator.isValid(rawDob.trim())){
            if (isRaw) {
                return rawDob;
            } else {
                return PRETTY_DATE_FORMATTER.format(RAW_DATE_FORMATTER.parse(rawDob.substring(0,8)));
            }
        } else {
            System.out.println(String.format("Invalid DOB; continue [%s]",rawDob));
            return EMPTY_STRING;
        }
    }
    public DOBFormatter(boolean isRaw,CustomValidator dobValidator) {
        this.isRaw = isRaw;
        this.dobValidator = dobValidator;
    }
}
