package com.epam.thread.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HookahBar {
    private static HookahBar instance;
    private static final Logger logger = LogManager.getLogger();
    private static final ReentrantLock instanceLock = new ReentrantLock();

    private static final int MAX_PEOPLE_INSIDE = 5;
    private static int amountOfPeopleInside;
    private static Deque<Customer> queueInside = new ArrayDeque<>();
    private static Deque<Customer> queueOutside = new ArrayDeque<>();

    private static final int HOOKAH_AMOUNT = 2;
    private Deque<Hookah> freeHookas = new ArrayDeque<>(HOOKAH_AMOUNT);

    private Lock locker = new ReentrantLock();
    private Deque<Condition> conditions = new ArrayDeque<>();

    private HookahBar() {
        for (int i = 0; i < HOOKAH_AMOUNT; i++) {
            freeHookas.add(new Hookah(i + 1));
        }
        amountOfPeopleInside = 0;
    }

    public static HookahBar getInstance() {
        if (instance == null) {
            instanceLock.lock();
            if (instance == null) {
                instance = new HookahBar();
            }
        }
        return instance;
    }

    public Hookah getHookah(Customer customer){
        Hookah hookah = null;
        try {
            locker.lock();
            if (freeHookas.isEmpty()) {
                Condition condition = locker.newCondition();
                if (queueOutside.isEmpty() && amountOfPeopleInside + customer.getAmountOfPeople() <= MAX_PEOPLE_INSIDE){
                    amountOfPeopleInside += customer.getAmountOfPeople();
                    queueInside.add(customer);
                    logger.info(customer + " is waiting inside" + amountOfPeopleInside);
                }
                else {
                    queueOutside.add(customer);
                    logger.info(customer + " is waiting outside");
                }
                conditions.add(condition);
                condition.await();
            }
            hookah = freeHookas.pop();
        } catch (InterruptedException e) {
            logger.error("Error while getting the hookah");
        } finally {
            locker.unlock();
        }
        return hookah;
    }

    public void returnHookah(Hookah hookah){
        try {
            locker.lock();
            freeHookas.add(hookah);
            if(!queueInside.isEmpty()){
                Condition condition = conditions.poll();
                if (condition != null) {
                    condition.signal();
                }
                amountOfPeopleInside -= queueInside.poll().getAmountOfPeople();
                if (!queueOutside.isEmpty()){
                    while (queueOutside.peekFirst().getAmountOfPeople() + amountOfPeopleInside <= MAX_PEOPLE_INSIDE){
                        Customer customer = queueOutside.poll();
                        amountOfPeopleInside += customer.getAmountOfPeople();
                        queueInside.add(customer);
                        logger.info(customer + " moved inside" + amountOfPeopleInside);
                        if (queueOutside.isEmpty()){
                            break;
                        }
                    }
                }
            }
        }
        finally {
            locker.unlock();
        }
    }
}
