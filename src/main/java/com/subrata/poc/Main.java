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
    final static String FILE_NAME4 = "test-optional-field-invalid-dob.txt";
    final static String FILE_NAME = "test-zero-pid-records.txt";
    final static String FILE_NAME2 = "test-required-field-missing.txt";
    final static String FILE_NAME3 = "test-optional-field-missing-gender.txt";
    final static String DELIMITER_REGEX = "|";
    final static String SEGMENT_IDENTIFIER = "PID";
    final static boolean RESULT_FORMAT_INDICATOR = false;

    public static void main(String[] args) {
        Instant start = Instant.now();
        Extractor pidExtractor = new PIDExtractor(RESULT_FORMAT_INDICATOR);
        CustomReader fileReader = new CustomFileReader(SEGMENT_IDENTIFIER, DELIMITER_REGEX, pidExtractor);
        List<SearchResponse> searchResponsesList = fileReader.read(FILE_PATH+ File.separator+FILE_NAME);
        System.out.println("Search Result Count-" + searchResponsesList.size());
        System.out.println("Search Result [Raw Data-" + RESULT_FORMAT_INDICATOR + "]" + searchResponsesList);
        System.out.println("Total Execution Time (ms)-" + Duration.between(start, Instant.now()).toMillis());
    }
}
