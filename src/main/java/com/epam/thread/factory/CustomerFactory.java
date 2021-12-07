package com.epam.thread.factory;

import com.epam.thread.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerFactory {
    private static final int PEOPLE_LIMIT = 3;

    public static Customer createCustomer(int amountOfPeople) {
        return new Customer(amountOfPeople);
    }

    public static List<Customer> createCustomerList(int number) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            customers.add(new Customer((int)(Math.random() * PEOPLE_LIMIT + 1)));
        }
        return customers;
    }
}
