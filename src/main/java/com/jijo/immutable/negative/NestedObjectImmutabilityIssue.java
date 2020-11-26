package com.jijo.immutable.negative;

/**
 * @author Jijo Lawrence on 25/11/2020
 *
 * Failure scenario: One of the nested object's instance variable is not final and so Object is not immutable
 *
 * NestedObjectLevel2.name is not final, which makes the entire object not immutable
 *
 */
public final class NestedObjectImmutabilityIssue {
    private final int id;
    private final String name;
    private final NestedObjectLevel1 nestedObjectLevel1;

    public NestedObjectImmutabilityIssue(int id, String name, NestedObjectLevel1 nestedObjectLevel1) {
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

        NestedObjectImmutabilityIssue simpleImmutable = new NestedObjectImmutabilityIssue(1, "jijo", nestedObjectLevel1);
        nestedObjectLevel2.setName("l2 modified");

        System.out.println(simpleImmutable.getNestedObjectLevel1().getNestedObjectLevel2().getName());
        //Notice that the output prints now 'l2 modified' even without assigning the nestedObjectLevel2 back to the nestedObjectLevel1 object and so to the simpleImmutable object
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
    private String name; //this is not final

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

    //this has a setter, which will be used to modify the object after initialization
    public void setName(String name) {
        this.name = name;
    }
}
