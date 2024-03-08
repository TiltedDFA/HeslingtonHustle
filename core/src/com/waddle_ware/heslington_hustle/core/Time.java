package com.waddle_ware.heslington_hustle.core;

//Used to manage time in a day
//16hours
public class Time
{
    /**
     * These constants will be used in core to determine the cost
     * of types of activities.
     * Since they are static they will be accessed with 'Time.', therefore
     * the Time part has been omitted from Time.
     */
    static final public int TimePerStudy = -120;
    static final public int TimePerRecreational = -120;
    static final public int TimePerFood = -60;
    /**
     * This private constant will be used to convert the current minutes
     * to an amount of intervals for updating the GUI layer
     */
    static final private int MinsInInterval = 15;
    /**
     * This constant variable will be used to specify the amount
     * of time that needs to pass irl for 1 unit of time to decrement
     */
    private final int   seconds_irl_to_decrement;
    private final int   game_minutes_per_interval;
    private int         minutes_remaining;

    public Time(int mins_per_inter, int secs_to_dec)
    {
        this.minutes_remaining = 60 * 16;
        this.game_minutes_per_interval = mins_per_inter;
        this.seconds_irl_to_decrement = secs_to_dec;
    }

    // This is going to convert the minute representation to bars
    public int GetIntervalsRemaining()
    {
        return (int) Math.ceil((double) this.minutes_remaining / MinsInInterval);
    }
    public ResourceExitConditions TryActivityType(ActivityType type)
    {
        int cost_of_resource;
        switch(type)
        {
            case Study:
                cost_of_resource = TimePerStudy;
                break;
            case Recreation:
                cost_of_resource = TimePerRecreational;
                break;
            case Food:
                cost_of_resource = TimePerFood;
                break;
            //This should never happen
            default:
                cost_of_resource = -99999999;
                break;
        }
        this.minutes_remaining += cost_of_resource;
        if(this.minutes_remaining < 0)
        {
            this.minutes_remaining -= cost_of_resource;
            return ResourceExitConditions.TooLow;
        }
        return ResourceExitConditions.IsOk;
    }
}