package com.subrata.poc.model;

public class SearchResponse {
    private String name;
    private String dob;
    private String gender;

    private SearchResponse(String name, String dob, String gender) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
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
                "name='" + name + '\'' +
                ", dob[YYYYMMDD]='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static class ResponseBuilder {
        // builder code
        private String name;
        private String dob;
        private String gender;

        public ResponseBuilder(String name) {
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
            return new SearchResponse(this.name, this.dob, this.gender);
        }
    }
}
