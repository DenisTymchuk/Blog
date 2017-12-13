package com.company.entity;

/**
 * Created by Oksa on 24.11.2017.
 */
public class Category {
    private long id;
    private String name;
    public static int number;

    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

