package com.jijo.hashmap.negative;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jijo Lawrence on 25/11/2020
 *
 * Failure scenario: ConcurrentHashMap with key null
 */
class ConcurrentHashMapKeyNull
{
    public static void main(String[] args)
    {
        ConcurrentHashMap m=new ConcurrentHashMap();
        m.put(100,"Hello");
        m.put(101,"Geeks");
        m.put(102,"Geeks");
        m.put(null,"Hello");
        System.out.println(m);
    }
}