package com.waddle_ware.heslington_hustle.core;

/**
 * This class combines exit conditions and resource
 * types to be able to returned from the core to
 * the gui, so it can provide a visual queue indicating
 * why an attempted interaction failed if it did.
 */
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
