package com.waddle_ware.heslington_hustle.core;


import java.time.Clock;

//Used to manage time in a day
//16hours
public class Time implements ResourceBase
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
    private final int   milliseconds_irl_to_decrement;
    private final int   game_minutes_per_decrement;
    private int         minutes_remaining;
    private final Clock timer;
    /**
     * This variable will be used to check if the time to decrement has elapsed
     */
    private long        end_point;

    public Time(int mins_per_dec, int secs_to_dec)
    {
        this.minutes_remaining = 60 * 16;
        this.game_minutes_per_decrement = mins_per_dec;
        this.milliseconds_irl_to_decrement = secs_to_dec * 1000;
        this.timer = Clock.systemUTC();
        this.end_point = this.timer.millis() + this.milliseconds_irl_to_decrement;
    }
    //responsible for passively draining time
    public void update()
    {
        if(this.end_point >= this.timer.millis()) return;
        this.minutes_remaining -= this.game_minutes_per_decrement;
        this.end_point += this.timer.millis() + this.milliseconds_irl_to_decrement;
    }
    // This is going to convert the minute representation to bars

    @Override
    public void reset()
    {
        this.minutes_remaining = 60 * 16;
    }

    public int getIntervalsRemaining()
    {
        if(this.minutes_remaining < 1) return 0;

        return (int) Math.ceil((double) this.minutes_remaining / MinsInInterval);
    }
    public int getMinutesRemaining()
    {
        if(this.minutes_remaining < 1) return 0;

        return this.minutes_remaining;
    }
    @Override
    public ExitConditions isOk(int amount)
    {
        if(amount < 0) return ExitConditions.TooLow;
        return ExitConditions.IsOk;
    }
    @Override
    public ResourceExitConditions tryActivityType(ActivityType type)
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
        final int potential_state = this.minutes_remaining + cost_of_resource;
        final ExitConditions condition = isOk(potential_state);
        return new ResourceExitConditions(ResourceTypes.Time, condition);
    }

    /**
     * An unchecked command that will do the activity
     * @param type the type of activity
     */
    @Override
    public void doActivity(ActivityType type)
    {
        switch(type)
        {
            case Study:
                this.minutes_remaining += TimePerStudy;
                return;
            case Recreation:
                this.minutes_remaining += TimePerRecreational;
                return;
            case Food:
                this.minutes_remaining += TimePerFood;
        }
    }
}