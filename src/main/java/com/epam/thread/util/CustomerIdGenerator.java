package com.epam.thread.util;

public class CustomerIdGenerator {
    private static int id;

    public static int generateId() {
        return ++id;
    }
}
