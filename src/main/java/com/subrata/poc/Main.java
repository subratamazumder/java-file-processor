package com.subrata.poc;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.extractor.impl.PIDExtractor;
import com.subrata.poc.service.reader.CustomReader;
import com.subrata.poc.service.reader.impl.CustomFileReader;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.subrata.poc.util.LoggerUtil.logBanner;
import static com.subrata.poc.util.LoggerUtil.logWarning;

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
        logBanner(System.lineSeparator() + System.lineSeparator() + "************************* Welcome To Java8 File Processor ****************************");
        logBanner(System.lineSeparator() + "Developed by Subrata Mazumder @ https://subratamazumder.github.io" + System.lineSeparator());
        Instant start = Instant.now();
        if (args.length < 3) {
            logWarning("Usage : $java -jar java-file-processor-<version>.jar <path to file> <Segment Identified {allowed only PID for now}> <formatSearchResult {allowed only true or false}>");
            logWarning("e.g.; TO Process PID Segment : java -jar java-file-processor-1.0-SNAPSHOT.jar PID /mydir/files/file.txt true");
            return;
        }
        String filePath = args[0];
        String segmentIdentifier = args[1];
        boolean resultFormatIndicator = Boolean.parseBoolean(args[2]);
        Extractor pidExtractor = new PIDExtractor(!resultFormatIndicator);
        CustomReader fileReader = new CustomFileReader(segmentIdentifier, DELIMITER_REGEX, pidExtractor);
//        CustomReader fileReader = new CustomFileReader(SEGMENT_IDENTIFIER, DELIMITER_REGEX, pidExtractor);
        List<SearchResponse> searchResponsesList = fileReader.read(filePath);
        System.out.println("Search Result Count-" + searchResponsesList.size());
        System.out.println("Search Result [Formatted Data-" + resultFormatIndicator + "]" + searchResponsesList);
        System.out.println("Total Execution Time (ms)-" + Duration.between(start, Instant.now()).toMillis());
    }
}
