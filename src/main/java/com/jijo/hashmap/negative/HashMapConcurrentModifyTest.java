package com.jijo.hashmap.negative;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jijo Lawrence on 25/11/2020
 *
 * Failure scenario: HashMap concurrent modification - multithreaded
 */
class HashMapConcurrentModifyTest extends Thread
{
    static Map<Integer,String> l=new HashMap<Integer,String>();

    public void run()
    {

        try
        {
            Thread.sleep(1000);
            // putting new element concurrently inside the map while a reading take place
            l.put(103,"D");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        l.put(100,"A");
        l.put(101,"B");
        l.put(102,"C");
        HashMapConcurrentModifyTest t=new HashMapConcurrentModifyTest();
        t.start();

        //reading concurrently from HashMap
        for (Object o : l.entrySet())
        {
            Object s=o;
            System.out.println(s);
            Thread.sleep(1000);
        }
        System.out.println(l);
    }
}
