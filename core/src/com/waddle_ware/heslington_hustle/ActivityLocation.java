package com.waddle_ware.heslington_hustle;

/**
 * The ActivityLocation class represents a location within the game where activities are.
 * It stores the coordinates of the location, interaction radius, and a name to identify it.
 */
public class ActivityLocation {
    private final float x; // The x-coordinate of the activity location
    private final float y; // The y-coordinate of the activity location
    private final float radius; // The radius of the activity location
    private final String name; // The name of the activity location

    /**
     * Constructs an ActivityLocation object with the specified coordinates, radius, and name.
     *
     * @param x      The x-coordinate of the activities location.
     * @param y      The y-coordinate of the activities location.
     * @param radius The interaction radius of the activity.
     * @param name   The name of the activity.
     */
    public ActivityLocation(float x, float y, float radius, String name) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.name = name;
    }

    /**
     * Retrieves the y-coordinate of the activity location.
     *
     * @return The y-coordinate of the activity location.
     */
    public float getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the activity location.
     *
     * @return The y-coordinate of the activity location.
     */
    public float getY() {
        return y;
    }

    /**
     * Retrieves the radius of the activity location.
     *
     * @return The radius of the activity location.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Retrieves the name of the activity location.
     *
     * @return The name of the activity location.
     */
    public String getName() {
        return name;
    }
}
