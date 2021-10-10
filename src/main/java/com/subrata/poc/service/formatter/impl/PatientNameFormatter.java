package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.service.formatter.DataFormatter;

public class PatientNameFormatter implements DataFormatter {
    private boolean isRaw;
    @Override
    public String format(String rawPatientName) {
        if (isRaw) {
            return rawPatientName;
        } else {
            return rawPatientName.replaceAll("\\^+", " ").trim();
        }
    }
    public PatientNameFormatter(boolean isRaw) {
        this.isRaw = isRaw;
    }
}
