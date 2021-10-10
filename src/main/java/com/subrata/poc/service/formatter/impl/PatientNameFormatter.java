package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.service.formatter.DataFormatter;
import com.subrata.poc.validator.CustomValidator;

public class PatientNameFormatter implements DataFormatter {
    private final boolean isRaw;
    private final CustomValidator patientNameValidator;
    @Override
    public String format(String rawPatientName) {
        String EMPTY_STRING = "";
        if(patientNameValidator.isValid(rawPatientName.trim())) {
            if (isRaw) {
                return rawPatientName;
            } else {
                return rawPatientName.replaceAll("\\^+", " ").trim();
            }
        } else {
            System.out.println(String.format("Invalid Name; continue [%s]",rawPatientName));
            return EMPTY_STRING;
        }
    }
    public PatientNameFormatter(boolean isRaw, CustomValidator patientNameValidator) {
        this.isRaw = isRaw;
        this.patientNameValidator = patientNameValidator;
    }
}
