package com.epam.thread.main;

import com.epam.thread.entity.Customer;
import com.epam.thread.entity.HookahBar;
import com.epam.thread.exception.MultithreadingException;
import com.epam.thread.factory.CustomerFactory;
import com.epam.thread.parser.CustomerParser;
import com.epam.thread.parser.impl.CustomerParserImpl;
import com.epam.thread.reader.CustomReader;
import com.epam.thread.reader.impl.CustomReaderImpl;
import com.epam.thread.validator.LineValidator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws MultithreadingException {
        CustomReader reader = new CustomReaderImpl();
        List<String> lines = reader.readLines("customers.txt");
        CustomerParser parser = new CustomerParserImpl();
        List<Customer> customers = lines.stream()
                .filter(LineValidator::validateLine)
                .map(parser::parseLine)
                .map(CustomerFactory::createCustomer)
                .collect(Collectors.toList());

        HookahBar hookahBar = HookahBar.getInstance();
        //customers.forEach(System.out::println);
        ExecutorService executor = Executors.newFixedThreadPool(customers.size());
        customers.forEach(executor::submit);
        executor.shutdown();
    }
}
