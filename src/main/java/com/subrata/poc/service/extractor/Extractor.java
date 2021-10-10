package com.subrata.poc.service.extractor;

import com.subrata.poc.model.SearchResponse;

public interface Extractor {
    SearchResponse extract(String lineWithPID, String delimiter);
}
