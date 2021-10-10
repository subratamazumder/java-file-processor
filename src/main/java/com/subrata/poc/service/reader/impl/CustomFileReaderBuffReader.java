package com.subrata.poc.service.reader.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.reader.CustomReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.subrata.poc.util.LoggerUtil.logError;
import static com.subrata.poc.util.LoggerUtil.logSuccess;

public class CustomFileReaderBuffReader implements CustomReader {
    private final String segmentIdentifier;
    private final String delimiterRegEx;
    private final Extractor pidExtractor;
    @Override
    public List<SearchResponse> read(String fileLocation) {
        Path path = Paths.get(fileLocation);
        List<SearchResponse> filteredLines = Collections.emptyList();
        logSuccess("File Name : " + fileLocation);
        logSuccess("Segment Identifier : " + this.getSegmentIdentifier());
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileLocation))) {
//            return readAllLines(reader);
//        }
//        try (Stream<String> lines = Files.lines(path)) { // UTF-8 only
            filteredLines = reader.lines()
                    .filter(
                            eachLine -> {
                                try {
                                    if (!eachLine.isEmpty() && eachLine.contains(segmentIdentifier))
                                        return true;
                                } catch (Exception anyException) {
                                    logError("Exception in Filtering records; continue", anyException);
                                }
                                return false;
                            }
                    )
                    .map(lineWithPID -> pidExtractor.extract(lineWithPID, delimiterRegEx))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logError("Exception in read records; Exit", e);
        }
        return filteredLines;
    }

    public String getSegmentIdentifier() {
        return segmentIdentifier;
    }

    public CustomFileReaderBuffReader(String segmentIdentifier, String delimiter, Extractor pidExtractor) {
        this.delimiterRegEx = "\\".concat(delimiter);
        this.pidExtractor = pidExtractor;
        this.segmentIdentifier = segmentIdentifier;
    }
}
