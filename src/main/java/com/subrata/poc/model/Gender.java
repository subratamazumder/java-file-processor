package com.subrata.poc.model;

public enum Gender {
    A("Ambiguous"),
    F("Female"),
    M("Male"),
    N("Not applicable"),
    O("Other"),
    U("Unknown");;
    public final String label;

    private Gender(String label) {
        this.label = label;
    }
}
