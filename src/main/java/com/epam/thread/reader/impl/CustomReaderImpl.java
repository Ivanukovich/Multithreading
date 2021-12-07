package com.epam.thread.reader.impl;

import com.epam.thread.exception.MultithreadingException;
import com.epam.thread.reader.CustomReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomReaderImpl implements CustomReader {
    private static final Logger logger = LogManager.getLogger();

    public List<String> readLines(String filePath) throws MultithreadingException {
        if (filePath == null) {
            logger.error("File path is null");
            throw new MultithreadingException("File path is null");
        }
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            logger.error("File path " + filePath + " is invalid");
            throw new MultithreadingException("File path " + filePath + " is invalid");
        }
        List<String> lines;
        try (Stream<String> linesStream = Files.lines(Paths.get(filePath))) {
            lines = linesStream
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error while reading the file " + filePath);
            throw new MultithreadingException("Error while reading the file " + filePath, e);
        }
        logger.info("Finish reading file " + filePath);
        return lines;
    }
}
