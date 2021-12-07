package com.epam.thread.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Hookah {
    private static final Logger logger = LogManager.getLogger();
    private final int hookahId;


    public Hookah(int hookahId){
        this.hookahId = hookahId;
    }

    public int getHookahId() {
        return hookahId;
    }

    public void use() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            logger.error("error");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Hookah hookah = (Hookah) o;

        return this.hookahId == hookah.hookahId;
    }

    @Override
    public int hashCode() {
        return hookahId;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Hookah ");
        result.append(hookahId);
        return String.valueOf(result);
    }
}
