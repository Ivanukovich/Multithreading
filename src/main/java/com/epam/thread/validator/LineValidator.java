package com.epam.thread.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LineValidator {
    private static final Logger logger = LogManager.getLogger();
    private final static String REGEX_CUSTOMER = "[123]";

    public static boolean validateLine(String line){
        if (line.matches(REGEX_CUSTOMER)){
            return true;
        }
        else {
            logger.info("Line \"" + line + "\" is invalid");
            return false;
        }
    }
}
