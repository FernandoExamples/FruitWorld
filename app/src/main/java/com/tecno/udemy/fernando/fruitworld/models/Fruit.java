package com.tecno.udemy.fernando.fruitworld.models;

public class Fruit {

    private int icon;
    private String name;
    private String origin;

    public Fruit(int icon, String name, String origin) {
        this.icon = icon;
        this.name = name;
        this.origin = origin;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }
}
