package com.waddle_ware.heslington_hustle.core;

public interface ResourceBase
{
    ExitConditions isOk(int amount);
    ResourceExitConditions tryActivityType(ActivityType type);
    void doActivity(ActivityType type);
    void reset();
}
