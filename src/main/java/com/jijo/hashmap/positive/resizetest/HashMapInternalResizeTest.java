package com.jijo.hashmap.positive.resizetest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jijo Lawrence on 25/11/2020
 *
 *  This class simulates the HashMap internal resizing done by Java
 *
 */
public class HashMapInternalResizeTest {
    public static void main(String[] args) {
        System.out.println("Scenario 1: *********** Default capacity initialization ***********");
        System.out.println("Scenario 1: *******************************************************");
        Map map = new HashMap();
        executeTest(map);

        System.out.println("Scenario 2: *********** Initial capacity 4 initialization ***********");
        System.out.println("Scenario 2: *********************************************************");
        map = new HashMap(4);
        executeTest(map);

        System.out.println("Scenario 3: *********** Initial capacity 16 (default), loadFactor changed to 50% initialization ***********");
        System.out.println("Scenario 3: ***********************************************************************************************");
        map = new HashMap(16, 0.50f);
        executeTest(map);

        System.out.println("Scenario 4: *********** Initial capacity 32, loadFactor changed to 40% initialization ***********");
        System.out.println("Scenario 4: *************************************************************************************");
        map = new HashMap(32, 0.40f);
        executeTest(map);

    }

    private static void executeTest(Map map) {
        System.out.println("Size checkpoint: 0 Initialized Map. Size of Map: " + getSizeOfHashMap(map));
        for (int i = 1; i < 16; ++i) {
            map.put(i, String.valueOf(i));
            System.out.printf("Size checkpoint: %d Added value: %s Size of Map: %d \n", i, map.get(i), getSizeOfHashMap(map));
        }
    }

    public static int getSizeOfHashMap(Map map) {
        Field field = null;
        Object[] table = null;
        try {
            field = HashMap.class.getDeclaredField("table");
            field.setAccessible(true);
            table = (Object[]) field.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return table == null ? 0 : table.length;
    }
}