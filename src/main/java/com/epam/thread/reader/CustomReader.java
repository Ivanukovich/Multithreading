package com.epam.thread.reader;

import com.epam.thread.exception.MultithreadingException;

import java.util.List;

public interface CustomReader {
    List<String> readLines(String filePath) throws MultithreadingException;
}
