package com.subrata.poc.service.writer.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.writer.CustomWriter;

import java.util.List;

import static com.subrata.poc.util.LoggerUtil.logSuccess;

public class CustomStdoutWriter implements CustomWriter {
    @Override
    public void write(List<SearchResponse> searchResponseList) {
        searchResponseList.stream().forEach(
                //{Name='grceue Patrice Natasha L', DOB[raw:YYYYMMDD, formatted :DD/MM/YY]='04/03/59', Gender='Female'}
                searchResponse -> logSuccess(searchResponse.toString())
        );
    }
}
