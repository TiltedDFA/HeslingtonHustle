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
    static final public int PerStudy = -120;
    static final public int PerRecreational = -120;
    static final public int PerFood = -60;
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

    public TimeManager(int mins_per_inter, int secs_to_dec)
    {
        this.minutes_remaining = 60 * 16;

    }
    // This is going to convert the minute representation to bars
    public int GetIntervalsRemaining()
    {
        return Math.ceil(this.minutes_remaining / MinsInInterval);
    }
}