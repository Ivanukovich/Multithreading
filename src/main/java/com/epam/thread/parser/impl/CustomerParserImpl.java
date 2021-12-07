package com.epam.thread.parser.impl;

import com.epam.thread.parser.CustomerParser;

public class CustomerParserImpl implements CustomerParser {
    @Override
    public int parseLine(String line) {
        return Integer.valueOf(line);
    }
}
