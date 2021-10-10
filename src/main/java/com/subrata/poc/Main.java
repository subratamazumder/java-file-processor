package com.subrata.poc;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.extractor.impl.PIDExtractor;
import com.subrata.poc.service.reader.CustomReader;
import com.subrata.poc.service.reader.impl.CustomFileReader;
import com.subrata.poc.service.reader.impl.CustomFileReaderBuffReader;
import com.subrata.poc.service.writer.CustomWriter;
import com.subrata.poc.service.writer.impl.CustomFileWriter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.subrata.poc.util.LoggerUtil.*;
import static com.subrata.poc.util.LoggerUtil.logSuccess;

public class Main {
    final static String DELIMITER_REGEX = "|";
    public static void main(String[] args) {
        logBanner(System.lineSeparator() + System.lineSeparator() + "************************* Welcome To Java8 File Processor ****************************");
        logBanner(System.lineSeparator() + "Developed by Subrata Mazumder @ https://subratamazumder.github.io" + System.lineSeparator());
        Instant start = Instant.now();
        if (args.length < 3) {
            logWarning("Usage : $java -jar java-file-processor-<version>.jar <path to file> <Segment Identified {allowed only PID for now}> <formatSearchResult {allowed only true or false}>");
            logWarning("e.g.; TO Process PID Segment : java -jar java-file-processor-1.0-SNAPSHOT.jar PID /mydir/files/file.txt true");
            return;
        }
        // extract command line args
        String filePath = args[0];
        String segmentIdentifier = args[1];
        boolean resultFormatIndicator = Boolean.parseBoolean(args[2]);

        List<SearchResponse> searchResponsesList = Collections.emptyList();
        switch (segmentIdentifier) {
            case "PID":
                Extractor pidExtractor = new PIDExtractor(!resultFormatIndicator);
                CustomReader fileReader = new CustomFileReader(segmentIdentifier, DELIMITER_REGEX, pidExtractor);
//                CustomReader fileReader = new CustomFileReaderBuffReader(segmentIdentifier, DELIMITER_REGEX, pidExtractor);
                searchResponsesList = fileReader.read(filePath);
                logSuccess("Read & Extraction Execution Time (ms) : " + Duration.between(start, Instant.now()).toMillis());
                CustomWriter customWriter = new CustomFileWriter(getOutPutFileName(filePath,resultFormatIndicator));
                customWriter.write(searchResponsesList);
                logSuccess("Write Execution Time (ms) : " + Duration.between(start, Instant.now()).toMillis());
                break;
            default:
                logWarning(String.format("Quiting, unsupported Segment Identifier : [%s]",segmentIdentifier));
                break;
        }
        logSuccess("Search Result Count : " + searchResponsesList.size());
        logSuccess("Total Execution Time (ms) : " + Duration.between(start, Instant.now()).toMillis());
    }
    private  static String getOutPutFileName(String inputFilePath, boolean resultFormatIndicator){
        Path path = Paths.get(inputFilePath);
        String fileDirectory = path.getParent().toString();
        String fileName = path.getFileName().toString();
        if (resultFormatIndicator){
            return fileDirectory.concat(File.separator).concat("formatted-output-").concat(fileName);
        } else {
            return fileDirectory.concat(File.separator).concat("raw-output-").concat(fileName);
        }
    }
}
