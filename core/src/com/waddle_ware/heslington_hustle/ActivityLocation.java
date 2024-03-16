package com.waddle_ware.heslington_hustle;

public class ActivityLocation {
    private float x;
    private float y;
    private final float radius;
    private final String name;

    public ActivityLocation(float x, float y, float radius, String name) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }
}
