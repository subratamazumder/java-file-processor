package com.subrata.poc.model;

public class SearchResponse {
    private String patientId; // unique identifier for audit/logging
    private String name;
    private String dob;
    private String gender;

    private SearchResponse(String patientId, String name, String dob, String gender) {
        this.patientId = patientId;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Response{" +
//                "ID='" + patientId + '\'' +", "+
                "Name='" + name + '\'' +", "+
                "DOB[raw:YYYYMMDD, formatted :DD/MM/YY]='" + dob + '\'' +", "+
                "Gender='" + gender + '\''+
                '}';
    }

    public static class ResponseBuilder {
        // builder code
        private String patientId;
        private String name;
        private String dob;
        private String gender;

        public ResponseBuilder(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
        }

        public ResponseBuilder withDob(String dob) {
            this.dob = dob;
            return this;
        }

        public ResponseBuilder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public SearchResponse build() {
            return new SearchResponse(this.patientId, this.name, this.dob, this.gender);
        }
    }
}
