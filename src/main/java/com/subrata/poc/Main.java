package com.subrata.poc;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.Extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    final static String FILE_PATH = "/Users/subratamazumder/workspace/file-processor";
    final static String FILE_NAME = "ORU_R01-4.txt";
    final static String DELIMETER_REGEX = "\\|";
    final static String TEST_RECORD = "PID|1||PATID5421^^^NIST MPI^MR||Wilson^Patrice^Natasha^^^^L||19820304|F||2106-3^White^HL70005|144 East 12th Street^^Los Angeles^CA^90012^^H||^PRN^PH^^^203^2290210|||||||||N^Not Hispanic or Latino^HL70189";
    final static boolean isRaw = false;

    public static void main(String[] args) {
        Instant start = Instant.now();
        Extractor extractor = new Extractor(isRaw);
        Path filePath = Paths.get(FILE_PATH, FILE_NAME);
        int recordNum = 0;
        try (Stream<String> lines = Files.lines(filePath)) {

            List<SearchResponse> filteredLines = lines
                    .filter(
                            eachLine -> {
                                try {
                                    if (!eachLine.isEmpty() && eachLine.contains("PID"))
                                        return true;
                                } catch (Exception anyException) {
                                    System.out.println("Exception in Filtering records; continue");
                                    anyException.printStackTrace();
                                }
                                return false;
                            }
                    )
                    .map(lineWithPID -> extractor.extract(lineWithPID, DELIMETER_REGEX))
                    .collect(Collectors.toList());
            System.out.println("Search Result Count-" + filteredLines.size());
            System.out.println("Search Result" + filteredLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total Execution Time (ms)-" + Duration.between(start, Instant.now()).toMillis());
    }
}
