package com.subrata.poc;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.extractor.impl.PIDExtractor;
import com.subrata.poc.service.reader.CustomReader;
import com.subrata.poc.service.reader.impl.CustomFileReader;
import com.subrata.poc.service.reader.impl.CustomFileReaderBuffReader;
import com.subrata.poc.service.writer.CustomWriter;
import com.subrata.poc.service.writer.impl.CustomFileWriter;
import com.subrata.poc.service.writer.impl.CustomStdoutWriter;

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
        Instant start = Instant.now();
        logBanner(System.lineSeparator() + System.lineSeparator() + "************************* Welcome To Java8 File Processor ****************************");
        logBanner(System.lineSeparator() + "Developed by Subrata Mazumder @ https://subratamazumder.github.io" + System.lineSeparator());

        if (args.length < 2) {
            logWarning("Usage : $java -jar java-file-processor-<version>.jar <path to file> <Segment Identified {allowed only PID for now}> <formatSearchResult {allowed only true or false (default=true)}> <writeToFile {allowed only true or false(default=false)}>");
            logWarning("e.g.; TO Process PID Segment : java -jar java-file-processor-1.0-SNAPSHOT.jar PID /mydir/files/file.txt true");
            logWarning("e.g.; TO Process PID Segment & Write to a File : java -jar java-file-processor-1.0-SNAPSHOT.jar PID /mydir/files/file.txt true true");
            return;
        }
        // extract command line args
        String filePath = args[0];
        String segmentIdentifier = args[1];
        logSuccess("Input File Name : " + filePath);
        logSuccess("Segment Identifier : " + segmentIdentifier);
        boolean resultFormatIndicator;
        boolean writeToFileIndicator;
        if (args.length >= 3) {
            resultFormatIndicator = Boolean.parseBoolean(args[2]);
        } else {
            resultFormatIndicator = true;
        }
        logSuccess("Search Result Formatting Option : " + resultFormatIndicator);
        if (args.length >= 4) {
            writeToFileIndicator = Boolean.parseBoolean(args[3]);
        } else {
            writeToFileIndicator = false;
        }
        logSuccess("Write To File Option : " + writeToFileIndicator);
        List<SearchResponse> searchResponsList = Collections.emptyList();
        switch (segmentIdentifier) {
            case "PID":
                Extractor pidExtractor = new PIDExtractor(!resultFormatIndicator);
                CustomReader fileReader = new CustomFileReader(segmentIdentifier, DELIMITER_REGEX, pidExtractor);
//                CustomReader fileReader = new CustomFileReaderBuffReader(segmentIdentifier, DELIMITER_REGEX, pidExtractor);
                searchResponsList = fileReader.read(filePath);
                CustomWriter outputWriter = null;
                if (writeToFileIndicator) {
                    outputWriter = new CustomFileWriter(getOutPutFileName(filePath, resultFormatIndicator));
                } else {
                    outputWriter = new CustomStdoutWriter();
                }
                outputWriter.write(searchResponsList);
                break;
            default:
                logWarning(String.format("Quiting, unsupported Segment Identifier : [%s]", segmentIdentifier));
                break;
        }
        logSuccess("Search Result Count : " + searchResponsList.size());
        logSuccess("Total Execution Time (ms) : " + Duration.between(start, Instant.now()).toMillis());
    }

    private static String getOutPutFileName(String inputFilePath, boolean resultFormatIndicator) {
        Path path = Paths.get(inputFilePath);
        String fileDirectory = path.getParent().toString();
        String fileName = path.getFileName().toString();
        if (resultFormatIndicator) {
            return fileDirectory.concat(File.separator).concat("formatted-output-").concat(fileName);
        } else {
            return fileDirectory.concat(File.separator).concat("raw-output-").concat(fileName);
        }
    }
}
