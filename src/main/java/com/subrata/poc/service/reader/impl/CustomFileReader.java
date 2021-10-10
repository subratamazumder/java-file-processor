package com.subrata.poc.service.reader.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.extractor.Extractor;
import com.subrata.poc.service.reader.CustomReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomFileReader implements CustomReader {
    private String filePath;
    private String fileName;
    private String delimiterRegEx;
    private Extractor pidExtractor;
    @Override
    public List<SearchResponse> read() {
        Path path = Paths.get(filePath, fileName);
        List<SearchResponse> filteredLines = Collections.emptyList();
        try (Stream<String> lines = Files.lines(path)) {
            filteredLines = lines
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
                    .map(lineWithPID -> pidExtractor.extract(lineWithPID, delimiterRegEx))
                    .collect(Collectors.toList());
            System.out.println("Search Result Count-" + filteredLines.size());
            System.out.println("Search Result [Raw Data-" + false + "]" + filteredLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;
    }

    public CustomFileReader(String filePath, String fileName, String delimiterRegEx, Extractor pidExtractor) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.delimiterRegEx = delimiterRegEx;
        this.pidExtractor = pidExtractor;
    }
}
