package com.subrata.poc.service;

import com.subrata.poc.model.Gender;
import com.subrata.poc.model.SearchResponse;

import java.time.format.DateTimeFormatter;

public class Extractor {
    private boolean isRaw;
    final private int PATIENT_ID_PID_SEQ = 3;
    final private int PATIENT_NAME_PID_SEQ = 5;
    final private int PATIENT_DOB_PID_SEQ = 7;
    final private int PATIENT_GENDER_PID_SEQ = 8;
    final private String RAW_DATE_FORMAT = "yyyyMMdd";
    final private String PRETTY_DATE_FORMAT = "dd/MM/yy";
    final DateTimeFormatter RAW_DATE_FORMATTER = DateTimeFormatter.ofPattern(RAW_DATE_FORMAT);
    final DateTimeFormatter PRETTY_DATE_FORMATTER = DateTimeFormatter.ofPattern(PRETTY_DATE_FORMAT);

    //do not throw exception
    public SearchResponse extract(String lineWithPID, String delimiter) {
        SearchResponse searchResponse = null;
        try {
            String[] splittedData = lineWithPID.split(delimiter);
            int itemCount = splittedData.length;
            printRecordArray(splittedData);
            searchResponse = new SearchResponse.ResponseBuilder(getPatientID(splittedData[PATIENT_ID_PID_SEQ]), getPatientName(splittedData[PATIENT_NAME_PID_SEQ]))
                    .withDob(getDob(splittedData[PATIENT_DOB_PID_SEQ]))
                    .withGender(getGenderFormatted(splittedData[PATIENT_GENDER_PID_SEQ]))
                    .build();
        } catch (Exception anyException) {
            System.out.println("Exception in Extractor.extract(); continue");
            anyException.printStackTrace();
        }
        return searchResponse;
    }

    private String getPatientID(String rawPatientID) {
        if (isRaw) {
            return rawPatientID;
        } else {
            return rawPatientID.replaceAll("\\^+", " ");
        }
    }

    private String getPatientName(String rawPatientName) {
        if (isRaw) {
            return rawPatientName;
        } else {
            return rawPatientName.replaceAll("\\^+", " ").trim();
        }
    }

    private String getDob(String rawDob) {

        if (isRaw) {
            return rawDob;
        } else {
            return PRETTY_DATE_FORMATTER.format(RAW_DATE_FORMATTER.parse(rawDob));
        }
    }

    private String getGenderFormatted(String rawGender) {
        if (isRaw) {
            return rawGender;
        } else {
            return Gender.valueOf(rawGender).label;
        }
    }

    //locally handy util method
    private void printRecordArray(String[] stringArray) {
        //System.out.println(Arrays.toString(stringArray));
        int position = 0;
        for (String item : stringArray) {
            System.out.println("PID" + position + ":" + item);
            position++;
        }
    }

    public Extractor(boolean isRaw) {
        this.isRaw = isRaw;
    }

}
