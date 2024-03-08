package com.waddle_ware.heslington_hustle.core;

import javax.swing.*;

public class Energy
{
    /**
     * Static constants
     */
    static final private int EnergyPerStudy = -2;
    static final private int EnergyPerRecreational = -1;
    static final private int EnergyPerFood = 1;
    /**
     * Member variables.
     */
    final private int limit;
    private int current;

    /**
     * Initialises the energy class with a default limit of 4 energy
     */
    public Energy()
    {
        this.limit = 4;
        this.current = this.limit;
    }
    /**
     * Initialises the energy class with a default limit of 4 energy
     *
     *  @param limit The upper limit of the energy that the player can have at any given time
     */
    public Energy(int limit)
    {
        this.limit = limit;
        this.current = limit;
    }
    /**
     * Attempts to add specified amount of energy to the current amount.
     *
     * @param amount The amount of energy to be added by the activity.
     */
    public ResourceExitConditions TryActivityType(ActivityType type)
    {
        int cost_of_resource;
        switch(type)
        {
            case Study:
                cost_of_resource = EnergyPerStudy;
                break;
            case Recreation:
                cost_of_resource = EnergyPerRecreational;
                break;
            case Food:
                cost_of_resource = EnergyPerFood;
                break;
                //This should never happen
            default:
                cost_of_resource = -99999999;
                break;
        }
        this.current += cost_of_resource;
        if(this.current < 0)
        {
            this.current -= cost_of_resource;
            return ResourceExitConditions.TooLow;
        }
        if(this.current > this.limit)
        {
            this.current -= cost_of_resource;
            return ResourceExitConditions.TooHigh;
        }
        return ResourceExitConditions.IsOk;
    }
    /**
     * Returns the current amount of energy.
     * @return the current amount of energy.
     */
    public int GetCurrentEnergy()
    {
        return this.current;
    }
    /**
     * Resets the energy to the limit.
     * Intended to be used upon the day resetting.
     */
    public void ResetEnergy()
    {
        this.current = this.limit;
    }
}
