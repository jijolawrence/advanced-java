package com.jijo.immutable.positive;

/**
 * @author Jijo Lawrence on 25/11/2020
 *
 * Scenario: how to achieve nested object immutability
 *
 * 1. Make class final
 * 2. Make all instance variables final
 * 3. Use constructor to assign values to instance variables
 * 4. Use only getters and avoid setters
 * 5. If in case nested objects need to be initialized from the super class, make sure to create a new object and use clone/copy.
 */
public final class NestedObjectImmutability {
    private final int id;
    private final String name;
    private final NestedObjectLevel1 nestedObjectLevel1;

    public NestedObjectImmutability(int id, String name, NestedObjectLevel1 nestedObjectLevel1) {
        this.id = id;
        this.name = name;
        this.nestedObjectLevel1 = nestedObjectLevel1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NestedObjectLevel1 getNestedObjectLevel1() {
        return nestedObjectLevel1;
    }

    public static void main(String[] args) {
        NestedObjectLevel2 nestedObjectLevel2 = new NestedObjectLevel2(1, "l2");
        NestedObjectLevel1 nestedObjectLevel1 = new NestedObjectLevel1(1, "l1", nestedObjectLevel2);

        NestedObjectImmutability simpleImmutable = new NestedObjectImmutability(1, "jijo", nestedObjectLevel1);

        nestedObjectLevel2 = new NestedObjectLevel2(99, "l2 modified");
        System.out.println(simpleImmutable.getNestedObjectLevel1().getNestedObjectLevel2().getName());
        //Notice that nestedObjectLevel2 once created, the same object cannot be modified.
        // If we need to have different values for nestedObjectLevel2, then the only option is to create another object, which guarantees the immutability.
    }

}

final class NestedObjectLevel1 {
    private final int id;
    private final String name;
    private final NestedObjectLevel2 nestedObjectLevel2;

    public NestedObjectLevel1(int id, String name, NestedObjectLevel2 nestedObjectLevel2) {
        this.id = id;
        this.name = name;
        this.nestedObjectLevel2 = nestedObjectLevel2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NestedObjectLevel2 getNestedObjectLevel2() {
        return nestedObjectLevel2;
    }
}

final class NestedObjectLevel2 {
    private final int id;
    private final String name;

    public NestedObjectLevel2(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
