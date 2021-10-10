package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.model.Gender;
import com.subrata.poc.service.formatter.DataFormatter;

public class GenderFormatter implements DataFormatter {
    private boolean isRaw;
    @Override
    public String format(String rawGender) {
        if (isRaw) {
            return rawGender;
        } else {
            return Gender.valueOf(rawGender).label;
        }
    }
    public GenderFormatter(boolean isRaw) {
        this.isRaw = isRaw;
    }
}
