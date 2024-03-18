package com.waddle_ware.heslington_hustle.core;

/**
 * Enum representing types of activities in the game.
 * This enum is used to identify different types of activities.
 */
public enum ActivityType {
    Study,
    Recreation,
    Food,
    Sleep;

    /**
     * Returns a string representation of the activity type.
     *
     * @return A string describing the activity type.
     */
    @Override
    public String toString() {
        switch (this) {
            case Recreation:
                return "Recreation";
            case Study:
                return "Study";
            case Food:
                return "Food";
            case Sleep:
                return "Sleep";
        }
        return "FAILED";
    }
}
