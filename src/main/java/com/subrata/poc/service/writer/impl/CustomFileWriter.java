package com.subrata.poc.service.writer.impl;

import com.subrata.poc.model.SearchResponse;
import com.subrata.poc.service.writer.CustomWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.subrata.poc.util.LoggerUtil.logError;
import static com.subrata.poc.util.LoggerUtil.logSuccess;

public class CustomFileWriter implements CustomWriter {
    final private String fileName;
    final private PrintWriter printWriter;

    public CustomFileWriter(String fileName) {
        this.fileName = fileName;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException ioException) {
            logError("Exception in initialising file writer; Exit", ioException);
        }
        this.printWriter = new PrintWriter(fileWriter);

    }

    @Override
    public void write(List<SearchResponse> searchResponseList) {
        try {
            searchResponseList.stream().forEach(
                    //{Name='grceue Patrice Natasha L', DOB[raw:YYYYMMDD, formatted :DD/MM/YY]='04/03/59', Gender='Female'}
                    searchResponse -> printWriter.printf(searchResponse.toString())
            );
        } catch (Exception ex) {
            printWriter.close();
        } finally {
            printWriter.close();
        }
        logSuccess("Output File Name : " + this.fileName);
    }
}
