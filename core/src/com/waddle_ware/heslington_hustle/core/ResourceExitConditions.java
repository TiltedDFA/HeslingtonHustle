package com.waddle_ware.heslington_hustle.core;

/**
 * Represents the combination of exit conditions and resource types
 * returned from the core to the GUI, providing visual cues for
 * why an attempted interaction failed, if applicable.
 */
public class ResourceExitConditions {
    private final ResourceTypes types;
    private final ExitConditions conditions;

    /**
     * Constructs a ResourceExitConditions object with the specified resource type and exit condition.
     *
     * @param types      The type of resource involved in the interaction.
     * @param conditions The exit condition indicating the outcome of the interaction.
     */
    public ResourceExitConditions(ResourceTypes types, ExitConditions conditions) {
        this.types = types;
        this.conditions = conditions;
    }

    /**
     * Gets the type of resource involved in the interaction.
     *
     * @return The type of resource.
     */
    public ResourceTypes getTypes() { return this.types;}

    /**
     * Gets the exit condition indicating the outcome of the interaction.
     *
     * @return The exit condition.
     */
    public ExitConditions getConditions() { return this.conditions;}
}
