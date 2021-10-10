package com.subrata.poc.service.writer;

import com.subrata.poc.model.SearchResponse;

import java.util.List;

public interface CustomWriter {
    void write(List<SearchResponse> searchResponseList);
}
