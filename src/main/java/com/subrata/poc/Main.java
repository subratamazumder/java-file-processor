package com.subrata.poc;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.extractor.impl.PIDExtractor;
import com.subrata.poc.service.reader.CustomReader;
import com.subrata.poc.service.reader.impl.CustomFileReader;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {
    final static String FILE_PATH = "/Users/subratamazumder/workspace/file-processor";
    final static String FILE_NAME = "ORU_R01-3.txt";
    final static String DELIMITER_REGEX = "|";
    final static String PID_SEGMENT_IDENTIFIER = "PID";
    final static String TEST_RECORD = "PID|1||PATID5421^^^NIST MPI^MR||Wilson^Patrice^Natasha^^^^L||19820304|F||2106-3^White^HL70005|144 East 12th Street^^Los Angeles^CA^90012^^H||^PRN^PH^^^203^2290210|||||||||N^Not Hispanic or Latino^HL70189";
    final static boolean RESULT_FORMAT_INDICATOR = false;

    public static void main(String[] args) {
        Instant start = Instant.now();
        Extractor pidExtractor = new PIDExtractor(RESULT_FORMAT_INDICATOR);
        CustomReader fileReader = new CustomFileReader(PID_SEGMENT_IDENTIFIER, DELIMITER_REGEX, pidExtractor);
        List<SearchResponse> searchResponsesList = fileReader.read(FILE_PATH+ File.separator+FILE_NAME);
        System.out.println("Search Result Count-" + searchResponsesList.size());
        System.out.println("Search Result [Raw Data-" + RESULT_FORMAT_INDICATOR + "]" + searchResponsesList);
        System.out.println("Total Execution Time (ms)-" + Duration.between(start, Instant.now()).toMillis());
    }
}
