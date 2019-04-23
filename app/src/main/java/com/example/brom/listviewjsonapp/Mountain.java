package com.example.brom.listviewjsonapp;

/**
 * Created by Anton on 2019-04-23.
 */

public class Mountain {
    private String name;
    private int height;
    private String location;

    public Mountain(String name, int height, String location) {
        this.name = name;
        this.height = height;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return name + " is located in " + location + " and is " + height + "m high.";
    }
}
