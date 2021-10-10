package com.subrata.poc.service.extractor.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.formatter.DataFormatter;
import com.subrata.poc.service.formatter.impl.DOBFormatter;
import com.subrata.poc.service.formatter.impl.GenderFormatter;
import com.subrata.poc.service.formatter.impl.PatientIDFormatter;
import com.subrata.poc.service.formatter.impl.PatientNameFormatter;

public class PIDExtractor implements Extractor {
    final private boolean isRaw;
    final private DataFormatter patientNameFormatter;
    final private DataFormatter dobFormatter;
    final private DataFormatter genderFormatter;
    final private DataFormatter patientIDFormatter;
    final private int PATIENT_ID_PID_SEQ = 3;
    final private int PATIENT_NAME_PID_SEQ = 5;
    final private int PATIENT_DOB_PID_SEQ = 7;
    final private int PATIENT_GENDER_PID_SEQ = 8;

    //do not throw exception back to streaming processing block
    public SearchResponse extract(String lineWithPID, String delimiter) {
        SearchResponse searchResponse = null;
        SearchResponse.ResponseBuilder responseBuilder;
        String patientId;
        String name;
        String dob;
        String gender;
        try {
            String[] splittedData = lineWithPID.split(delimiter);
            int itemCount = splittedData.length;
            printRecordArray(splittedData);
            //extract patientId & patientName - required field
            if(itemCount >=PATIENT_NAME_PID_SEQ) {
                patientId = patientIDFormatter.format(splittedData[PATIENT_ID_PID_SEQ]);
                name = patientNameFormatter.format(splittedData[PATIENT_NAME_PID_SEQ]);
                responseBuilder = new SearchResponse.ResponseBuilder(patientId,name);
                //extract dob - optional field
                if(itemCount >=PATIENT_DOB_PID_SEQ) {
                    dob = dobFormatter.format(splittedData[PATIENT_DOB_PID_SEQ]);
                    responseBuilder = responseBuilder.withDob(dob);
                }
                //extract gender - optional field
                if(itemCount >=PATIENT_GENDER_PID_SEQ) {
                    gender = genderFormatter.format(splittedData[PATIENT_GENDER_PID_SEQ]);
                    responseBuilder = responseBuilder.withGender(gender);
                }
                //build final object
                searchResponse = responseBuilder.build();
            } else {
                //log error continue
                System.out.println("Required fields are missing");
            }
        } catch (Exception anyException) {
            System.out.println("Exception in Extractor.extract(); continue");
            anyException.printStackTrace();
        }
        return searchResponse;
    }

    //local handy util method for printing only
    private void printRecordArray(String[] stringArray) {
        //System.out.println(Arrays.toString(stringArray));
        int position = 0;
        for (String item : stringArray) {
            System.out.println("PID" + position + ":" + item);
            position++;
        }
    }

    public boolean isRaw() {
        return isRaw;
    }

    public PIDExtractor(boolean isRaw) {
        this.isRaw = isRaw;
        patientNameFormatter = new PatientNameFormatter(isRaw);
        dobFormatter = new DOBFormatter(isRaw);
        genderFormatter = new GenderFormatter(isRaw);
        patientIDFormatter = new PatientIDFormatter(isRaw);
    }

}
