package com.subrata.poc.service.reader.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.reader.CustomReader;

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

public class CustomFileReader implements CustomReader {
    private final String segmentIdentifier;
    private final String delimiterRegEx;
    private final Extractor pidExtractor;
    @Override
    public List<SearchResponse> read(String fileLocation) {
//        Instant start = Instant.now();
        Path path = Paths.get(fileLocation);
        List<SearchResponse> filteredLines = Collections.emptyList();
        try (Stream<String> lines = Files.lines(path)) { // UTF-8 only
            filteredLines = lines
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
            logError("Exception in read records; exit", e);
        }
//        logSuccess("Read & Extraction Execution Time (ms) : " + Duration.between(start, Instant.now()).toMillis());
        return filteredLines;
    }

    public String getSegmentIdentifier() {
        return segmentIdentifier;
    }

    public CustomFileReader(String segmentIdentifier, String delimiter, Extractor pidExtractor) {
        this.delimiterRegEx = "\\".concat(delimiter);
        this.pidExtractor = pidExtractor;
        this.segmentIdentifier = segmentIdentifier;
    }
}
