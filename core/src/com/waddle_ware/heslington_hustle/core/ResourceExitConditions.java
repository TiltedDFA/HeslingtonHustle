package com.waddle_ware.heslington_hustle.core;

public class ResourceExitConditions
{
    private final ResourceTypes types;
    private final ExitConditions conditions;

    public ResourceExitConditions(ResourceTypes types, ExitConditions conditions)
    {
        this.types = types;
        this.conditions = conditions;
    }

    public ResourceTypes GetTypes() { return this.types;}

    public ExitConditions GetConditions() { return this.conditions;}
}
