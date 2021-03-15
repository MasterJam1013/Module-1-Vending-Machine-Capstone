package com.techelevator;

public enum ItemType {
    CHIP("Chip"), CANDY("Candy"), DRINK("Drink"), GUM("Gum");

    private String name;

    ItemType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
