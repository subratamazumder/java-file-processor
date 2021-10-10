package com.subrata.poc.service.formatter.impl;

import com.subrata.poc.service.formatter.DataFormatter;

import java.time.format.DateTimeFormatter;

public class DOBFormatter implements DataFormatter {
    final private String RAW_DATE_FORMAT = "yyyyMMdd";
    final private String PRETTY_DATE_FORMAT = "dd/MM/yy";
    final DateTimeFormatter RAW_DATE_FORMATTER = DateTimeFormatter.ofPattern(RAW_DATE_FORMAT);
    final DateTimeFormatter PRETTY_DATE_FORMATTER = DateTimeFormatter.ofPattern(PRETTY_DATE_FORMAT);
    private boolean isRaw;
    @Override
    public String format(String rawDob) {
        if (isRaw) {
            return rawDob;
        } else {
            return PRETTY_DATE_FORMATTER.format(RAW_DATE_FORMATTER.parse(rawDob));
        }
    }
    public DOBFormatter(boolean isRaw) {
        this.isRaw = isRaw;
    }
}
