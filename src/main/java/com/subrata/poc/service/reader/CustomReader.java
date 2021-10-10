package com.subrata.poc.service.reader;

import com.subrata.poc.model.SearchResponse;

import java.util.List;

public interface CustomReader {
    List<SearchResponse> read(String fileLocation);
}
