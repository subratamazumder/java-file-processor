package com.subrata.poc.validator.impl;

import com.subrata.poc.validator.CustomValidator;

import java.util.regex.Pattern;

public class GenderValidator implements CustomValidator {
    @Override
    public boolean isValid(String gender) {
        Pattern pattern = Pattern.compile("A|F|M|N|O|U");
        return gender.isEmpty() || pattern.matcher(gender).find();
    }
}
