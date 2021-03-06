package com.jijo.multithread.counter.positive.sync;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jijo Lawrence on 25/11/2020
 * <p>
 * Scenario: Counter class supposed to give an incremental counter value for each thread.
 * Used synchronized keyword for Counter.getCount() method to make the Counter class threadsafe.
 * <p>
 * Tip: Cannot give a package name called synchronized
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

    private int count;

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

    /*
     * This method changed to synchronized to make it threadsafe
     */
    public synchronized int getCount() {
        //tmp variable used to make sure the DuplicateCounterTracker gets the correct count value
        int tmp = ++count;
        DuplicateCounterTracker.getInstance().updateDuplicateTracker(tmp);
        return tmp;
    }

}

final class DuplicateCounterTracker {
    private static volatile DuplicateCounterTracker sDuplicateTrackerInstance;

    private DuplicateCounterTracker() {
        if (sDuplicateTrackerInstance != null) {
            throw new RuntimeException("Use the getInstance() method to get the singleton instance");
        }
    }

    public static DuplicateCounterTracker getInstance() {
        if (sDuplicateTrackerInstance == null) {
            synchronized (DuplicateCounterTracker.class) {
                if (sDuplicateTrackerInstance == null) {
                    sDuplicateTrackerInstance = new DuplicateCounterTracker();
                }
            }
        }
        return sDuplicateTrackerInstance;
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
