package com.waddle_ware.heslington_hustle.core;

public interface ResourceBase
{
    public ExitConditions isOk(int amount);
    public ResourceExitConditions tryActivityType(ActivityType type);
    void doActivity(ActivityType type);
    void reset();
}
