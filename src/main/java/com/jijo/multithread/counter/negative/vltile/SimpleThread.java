package com.jijo.multithread.counter.negative.vltile;

import java.util.Set;
import java.util.TreeSet;

/**
 *  @author Jijo Lawrence on 25/11/2020
 *
 *
 *
 * Failure Scenario: Counter class supposed to give an incremental counter value for each thread.
 * Used volatile int to see whether it makes it thread safe.
 *
 * Result: NOT Thread Safe
 *
 * Tip: Cannot give a package name called volatile
 *
 */
public class SimpleThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Thread Name: " + Thread.currentThread().getName() + " Counter value: " + Counter.getInstance().getCount());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            Thread thread = new Thread(new SimpleThread());
            thread.start();
        }
    }
}

/**
 * Counter singleton class
 */
final class Counter {
    private static volatile Counter sCounterInstance;

    private volatile int count;

    private Counter() {
        if (sCounterInstance != null) {
            throw new RuntimeException("Use the getInstance() method to get the singleton instance");
        }
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
        //tmp variable used to make sure the DuplicateCounterTracker gets the correct count value
        int tmp = ++count;
        DuplicateCounterTracker.getInstance().updateDuplicateTracker(tmp);
        return tmp;
    }
}

/**
 * This class is just to track duplicate counters
 */
final class DuplicateCounterTracker {
    private static DuplicateCounterTracker INSTANCE = new DuplicateCounterTracker();

    private DuplicateCounterTracker() {
    }

    public static DuplicateCounterTracker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DuplicateCounterTracker();
        }
        return INSTANCE;
    }

    //This is just to track the duplicate easily and normally a counter class will not contain such an instance variable
    private Set duplicateTracker = new TreeSet<>();
    ;

    public synchronized void updateDuplicateTracker(int count) {
        boolean result = false;
        result = duplicateTracker.add(count);
        if (result == false) {
            System.err.println("Duplicate value : " + count + " detected in thread: " + Thread.currentThread().getName());
        }
    }

}
