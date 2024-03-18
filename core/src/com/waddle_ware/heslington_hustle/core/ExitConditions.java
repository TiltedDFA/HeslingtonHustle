package com.waddle_ware.heslington_hustle.core;

/**
 * Enum representing the conditions of a request to a resource.
 * This is used by resources to determine what the condition of a request
 * to a resource is. Since the resources will have internal representations
 * of the cost of types of activities and the quantity of the resources will
 * only be changed by a TryActivityType function that accepts activity types.
 * Therefore, the need to know whether the activity succeeded or if it failed
 * why it failed will is handled by the TryActivityType functions returning
 * one of these exit statuses. If it's too low or too high this anticipates
 * the GUI giving back some visual feedback of why the activity failed.
 */
public enum ExitConditions {
    IsOk,
    TooLow,
    TooHigh;

    /**
     * Returns a string representation of the exit condition.
     *
     * @return A string describing the exit condition.
     */
    @Override
    public String toString() {
        switch (this) {
            case IsOk:
                return "Is ok";
            case TooLow:
                return "Was too low";
            case TooHigh:
                return "Was too high";
        }
        return "FAILED";
    }
}
