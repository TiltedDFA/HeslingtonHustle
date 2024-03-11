package com.waddle_ware.heslington_hustle.core;

public interface ResourceBase
{
    /**
     * An Internal function to check whether the amount of
     * a resource specified would be ok.
     * @param amount an amount of energy
     * @return ExitCondition(TooLow, TooHigh, IsOK) indicating
     * if there is an issue with the amount of energy passed in.
     */
    ExitConditions isOk(int amount);
    /**
     * This function attempts the current type of activity passed in.
     * @param type The type of activity that the player is attempting to do
     * @return ResourceExitCondition signed with the resource type and an
     * exit condition indicating whether it is possible to do this activity
     */
    ResourceExitConditions tryActivityType(ActivityType type);
    /**
     * An unchecked function that will do the activity
     * @param type the type of activity
     */
    void doActivity(ActivityType type);
    /**
     * Resets the resource to the starting state.
     * Intended to be used upon the day resetting.
     */
    void reset();
}
