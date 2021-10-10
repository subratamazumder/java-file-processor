package com.subrata.poc.validator.impl;

import com.subrata.poc.validator.CustomValidator;

public class PatientNameValidator implements CustomValidator {
    @Override
    public boolean isValid(String name) {
        //atleast 1 max 250
        return !name.isEmpty() && name.length() >= 1 &&  name.length()<=250;
    }
}
