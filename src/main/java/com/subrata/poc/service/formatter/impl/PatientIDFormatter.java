package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.service.formatter.DataFormatter;

public class PatientIDFormatter implements DataFormatter {
    private final boolean isRaw;
    @Override
    public String format(String rawPatientName) {
        if (isRaw) {
            return rawPatientName;
        } else {
            return rawPatientName.replaceAll("\\^+", " ").trim();
        }
    }
    public PatientIDFormatter(boolean isRaw) {
        this.isRaw = isRaw;
    }
}
