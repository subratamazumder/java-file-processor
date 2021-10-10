package com.subrata.poc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;


class MainTest {
    void happyPath() {
        //Format: YYYY[MM[DD[HH[MM[SS[.S[S[S[S]]]]]]]]][+/-ZZZZ].
//        String dob1="19820304115555";
//        String dob="19820304";
//        DateTimeFormatter customPIDDOBFormatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        DateTimeFormatter customPIDDOBFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        System.out.println(customPIDDOBFormatter.parse(dob));
//        System.out.println(DateTimeFormatter.ofPattern("dd-MM-yy")
//                .format(customPIDDOBFormatter.parse(dob)));
        createTestFile();
    }

    private void createTestFile() {
        Path path = Paths.get("/Users/subratamazumder/workspace/file-processor/ORU_R01-5.txt");
        //add 4 digit patined id, 6 charecter fname ,4 digit birth year
        String TEST_RECORD_FORMAT = "PID|1||PATID%s^^^NIST MPI^MR||%s^Patrice^Natasha^^^^L||%s0304|F||2106-3^White^HL70005|144 East 12th Street^^Los Angeles^CA^90012^^H||^PRN^PH^^^203^2290210|||||||||N^Not Hispanic or Latino^HL70189\n";
        Random random = new Random();
        int noOfline = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            while (noOfline < 1000000) {
                writer.write(String.format(TEST_RECORD_FORMAT, String.format("%04d", random.nextInt(9999)), getRandomFirstName(), getRandomYear()));
                noOfline++;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String getRandomFirstName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
    private String getRandomYear() {
        int leftLimit = 1700;
        int rightLimit = 2021;
        Random random = new Random();
        int year = random.nextInt(rightLimit - leftLimit) + leftLimit;
        return String.valueOf(year);
    }
}