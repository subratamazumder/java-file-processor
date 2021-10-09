package com.subrata.poc.service;

import com.subrata.poc.model.SearchResponse;

import java.util.Arrays;

public class Extractor {
    private boolean isRaw;
    final private int PATIENT_NAME_PID_SEQ=5;
    final private int PATIENT_DOB_PID_SEQ=7;
    final private int PATIENT_GENDER_PID_SEQ=8;
    //do not throw exception
    public SearchResponse extract(String lineWithPID, String delimeter){
        SearchResponse searchResponse = null;
        try {
            String[] splittedData = lineWithPID.split(delimeter);
            System.out.println(Arrays.toString(splittedData));
            searchResponse = new SearchResponse.ResponseBuilder(getPatientNameFormatted(splittedData[PATIENT_NAME_PID_SEQ]))
                    .withDob(getDobFormatted(splittedData[PATIENT_DOB_PID_SEQ]))
                    .withGender(getGenderFormatted(splittedData[PATIENT_GENDER_PID_SEQ]))
                    .build();
        } catch (Exception anyException){
            System.out.println("Exception in Extractor.extract(); continue");
            anyException.printStackTrace();
        }
        return searchResponse;
    }
    private String getPatientNameFormatted(String rawPatientName){
        if (isRaw){
            return rawPatientName;
        } else {
            return rawPatientName.replaceAll("\\^+"," ");
        }
    }
    private String getDobFormatted(String rawDob){
        return rawDob;
    }
    private String getGenderFormatted(String rawGender){
        return rawGender;
    }
    public Extractor(boolean isRaw) {
        this.isRaw = isRaw;
    }
}
