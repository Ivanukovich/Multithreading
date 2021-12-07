package com.epam.thread.entity;

import com.epam.thread.util.CustomerIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable{
    private static final Logger logger = LogManager.getLogger();
    private final int customerId;
    private final int amountOfPeople;

    public Customer(int amountOfPeople) {
        this.customerId = CustomerIdGenerator.generateId();
        this.amountOfPeople = amountOfPeople;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAmountOfPeople() {
        return amountOfPeople;
    }

    @Override
    public void run() {
        Hookah hookah  = HookahBar.getInstance().getHookah(this);
        logger.info(this + " got " + hookah);
        for (int i = 0; i < amountOfPeople; ++i) {
            hookah.use();
        }
        HookahBar.getInstance().returnHookah(hookah);
        logger.info(this + " returned " + hookah);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Customer customer = (Customer) o;

        return this.customerId == customer.customerId;
    }

    @Override
    public int hashCode() {
        return customerId * 31 + amountOfPeople;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Customer ");
        result.append(customerId);
        if (amountOfPeople > 1) {
            result.append(" (");
            result.append(amountOfPeople);
            result.append(" people)");
        }
        return String.valueOf(result);
    }
}
