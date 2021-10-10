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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomFileReader implements CustomReader {
    private String segmentIdentifier;
    private String delimiterRegEx;
    private Extractor pidExtractor;
    @Override
    public List<SearchResponse> read(String fileLocation) {
        Path path = Paths.get(fileLocation);
        List<SearchResponse> filteredLines = Collections.emptyList();
        try (Stream<String> lines = Files.lines(path)) {
            filteredLines = lines
                    .filter(
                            eachLine -> {
                                try {
                                    if (!eachLine.isEmpty() && eachLine.contains(segmentIdentifier))
                                        return true;
                                } catch (Exception anyException) {
                                    System.out.println("Exception in Filtering records; continue");
                                    anyException.printStackTrace();
                                }
                                return false;
                            }
                    )
                    .map(lineWithPID -> pidExtractor.extract(lineWithPID, delimiterRegEx))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;
    }

    public CustomFileReader(String segmentIdentifier, String delimiter, Extractor pidExtractor) {
        this.delimiterRegEx = "\\".concat(delimiter);
        this.pidExtractor = pidExtractor;
        this.segmentIdentifier = segmentIdentifier;
    }
}
