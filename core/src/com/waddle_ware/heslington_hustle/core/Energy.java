package com.waddle_ware.heslington_hustle.core;


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
     * Returns the current amount of energy.
     * @return the current amount of energy.
     */
    public int getCurrentEnergy()
    {
        return this.current;
    }
    /**
     * Resets the energy to the limit.
     * Intended to be used upon the day resetting.
     */
    public void reset()
    {
        this.current = this.limit;
    }
    private ExitConditions isOk(int amount)
    {
        if(amount < 0) return ExitConditions.TooLow;
        if(amount > this.limit) return ExitConditions.TooHigh;
        return ExitConditions.IsOk;
    }
    /**
     * Attempts to add specified amount of energy to the current amount.
     *
     */
    public ResourceExitConditions tryActivityType(ActivityType type)
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
        final int potential_state = this.current + cost_of_resource;
        final ExitConditions condition = isOk(potential_state);
        return new ResourceExitConditions(ResourceTypes.Energy, condition);
    }
    /**
     * An unchecked command that will do the activity
     * @param type the type of activity
     */
    void doActivity(ActivityType type)
    {
        switch(type)
        {
            case Study:
                this.current += EnergyPerStudy;
                return;
            case Recreation:
                this.current += EnergyPerRecreational;
                return;
            case Food:
                this.current += EnergyPerFood;
        }
    }
}
