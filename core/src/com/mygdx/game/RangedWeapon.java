package com.mygdx.game;

public class RangedWeapon {
    public enum Type {
        BOW,
        // Add more types of ranged weapons here, if needed
    }

    private Type type;
    private String name;
    private int damage;
    // Add more properties specific to ranged weapons, e.g., range, attack speed, etc.

    public RangedWeapon(Type type, String name, int damage) {
        this.type = type;
        this.name = name;
        this.damage = damage;
    }

    public Type getType() {
        return type;
    }

    // Add getter and setter methods for the properties, if needed

    // Add any additional methods relevant to ranged weapons
}
