package com.subrata.poc.validator.impl;

import com.subrata.poc.validator.CustomValidator;

public class DobValidator implements CustomValidator {
    @Override
    public boolean isValid(String rawDob) {
        //atleast 8 digit numeric YYYYMMDD
        return rawDob.length() >= 8 && rawDob.length() <= 26 && rawDob.matches("-?\\d+(\\.\\d+)?");
    }
}
