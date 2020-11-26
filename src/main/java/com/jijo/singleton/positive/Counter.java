package com.jijo.singleton.positive;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jijo Lawrence on 26/11/2020
 *
 * Counter singleton class
 */
public final class Counter {
    private static volatile Counter sCounterInstance;

    private AtomicInteger count;

    private Counter() {
        if (sCounterInstance != null) {
            throw new RuntimeException("Use the getInstance() method to get the singleton instance");
        }
        count = new AtomicInteger();
    }

    public static Counter getInstance() {
        if (sCounterInstance == null) {
            synchronized (Counter.class) {
                if (sCounterInstance == null) {
                    sCounterInstance = new Counter();
                }
            }
        }
        return sCounterInstance;
    }

    public int getCount() {
        return count.incrementAndGet();
    }

}

