package com.jijo.observable.multithreaded.positive;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  @author Jijo Lawrence on 25/11/2020
 *
 * Scenario: Counter class as Observable and duplicate tracker as Observer.
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
final class Counter extends Observable {
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
                    sCounterInstance.addObserver(DuplicateCounterTracker.getInstance());
                }
            }
        }
        return sCounterInstance;
    }

    public int getCount() {
        //tmp variable used to make sure the DuplicateCounterTracker gets the correct count value
        int tmp = count.incrementAndGet();
        setChanged();
        notifyObservers(tmp);
        return tmp;
    }

}

/**
 * This class is just to track duplicate counters
 */
final class DuplicateCounterTracker implements Observer {
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

    @Override
    public void update(Observable o, Object arg) {
        updateDuplicateTracker(((Integer)arg));
    }
}
