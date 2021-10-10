package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.model.Gender;
import com.subrata.poc.service.formatter.DataFormatter;
import com.subrata.poc.validator.CustomValidator;

import static com.subrata.poc.util.LoggerUtil.logWarning;

public class GenderFormatter implements DataFormatter {
    private final boolean isRaw;
    private final CustomValidator genderValidator;

    @Override
    public String format(String rawGender) {
        String EMPTY_STRING = "";
        //optional field can be empty
        if(rawGender.trim().isEmpty()){
            return EMPTY_STRING;
        }
        if(genderValidator.isValid(rawGender.trim())) {
            if (isRaw) {
                return rawGender;
            } else {
                return Gender.valueOf(rawGender).label;
            }
        } else {
            logWarning(String.format("Invalid Gender; continue [%s]",rawGender));
            return EMPTY_STRING;
        }
    }

    public GenderFormatter(boolean isRaw, CustomValidator genderValidator) {
        this.isRaw = isRaw;
        this.genderValidator = genderValidator;
    }
}
