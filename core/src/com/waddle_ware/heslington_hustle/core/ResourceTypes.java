package com.waddle_ware.heslington_hustle.core;

/**
 * Enum representing different types of resources in the game.
 * Currently, the available resource types are Time and Energy.
 */
public enum ResourceTypes {
    Time,
    Energy;

    /**
     * Returns a string representation of the resource type.
     *
     * @return A string representing the resource type.
     */
    @Override
    public String toString() {
        switch (this) {
            case Time:
                return "Time";
            case Energy:
                return "Energy";
        }
        return "FAILED";
    }
}
