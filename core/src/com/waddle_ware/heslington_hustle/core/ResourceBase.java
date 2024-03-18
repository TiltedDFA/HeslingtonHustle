package com.waddle_ware.heslington_hustle.core;

/**
 * The ResourceBase interface defines methods for managing game resources.
 * It provides functionality to check the availability of resources,
 * attempt activities, perform activities, and reset resources.
 */
public interface ResourceBase {

    /**
     * Checks whether the specified amount of a resource is sufficient.
     *
     * @param amount The amount of the resource to check.
     * @return An ExitCondition(TooLow, TooHigh, IsOK) indicating if there are any issues with the amount of the resource.
     */
    ExitConditions isOk(int amount);

    /**
     * Attempts to perform the specified type of activity with the resource.
     *
     * @param type The type of activity to attempt.
     * @return A ResourceExitConditions object indicating whether it is possible to perform the activity.
     */
    ResourceExitConditions tryActivityType(ActivityType type);

    /**
     * An unchecked function to perform the specified type of activity.
     *
     * @param type The type of activity.
     */
    void doActivity(ActivityType type);

    /**
     * Resets the resource to its initial state.
     * Intended to be used when the day resets.
     */
    void reset();
}
