package com.waddle_ware.heslington_hustle.core;


public class Energy
{
    /**
     * Static constants
     */
    static final private int ENERGY_PER_STUDY = -2;
    static final private int ENERGY_PER_RECREATIONAL = -1;
    static final private int ENERGY_PER_FOOD = 1;
    /**
     * Member variables.
     */
    final private int limit;
    private int current;
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
                cost_of_resource = ENERGY_PER_STUDY;
                break;
            case Recreation:
                cost_of_resource = ENERGY_PER_RECREATIONAL;
                break;
            case Food:
                cost_of_resource = ENERGY_PER_FOOD;
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
                this.current += ENERGY_PER_STUDY;
                return;
            case Recreation:
                this.current += ENERGY_PER_RECREATIONAL;
                return;
            case Food:
                this.current += ENERGY_PER_FOOD;
        }
    }
}
