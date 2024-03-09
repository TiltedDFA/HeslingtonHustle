package com.waddle_ware.heslington_hustle.core;

/**
 * This is used by resources to determine what the condition of a request
 * to a resource is. Since the resources will have internal representation
 * of the cost of types of activities and the quantity of the resources will
 * only be changed by a TryActivityType function that accepts activity types.
 * Therefore, the need to know whether the activity succeeded or if it failed
 * why it failed will is handled by the TryActivityType functions returning
 * one of these exit statuses. If it's too low or too high this anticipates
 * the GUI giving back some visual feedback of why the activity failed.
 */
public enum ExitConditions
{
    IsOk,
    TooLow,
    TooHigh
}
