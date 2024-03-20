package com.waddle_ware.heslington_hustle.core;

import java.time.Clock;

/**
 * The Time class manages time in the game, representing a day with a specific duration (16 hours).
 * It implements the ResourceBase interface for resource management.
 */
public class Time implements ResourceBase {
    static final private int TIME_PER_STUDY = -120;
    static final private int TIME_PER_RECREATIONAL = -120;
    static final private int TIME_PER_FOOD = -60;
    static final public int MINUTES_PER_DAY = 16 * 60;

    /**
     * This private constant will be used to convert the current minutes
     * to an amount of intervals for updating the GUI layer
     */
    static final private int MINS_IN_INTERVAL = 15;

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

    /**
     * Constructs a Time instance with specified parameters.
     *
     * @param mins_per_dec      The number of game minutes to decrement in each update cycle.
     * @param secs_to_dec       The number of real-time seconds to decrement for each game time decrement.
     */
    public Time(int mins_per_dec, int secs_to_dec) {
        this.minutes_remaining = MINUTES_PER_DAY;
        this.game_minutes_per_decrement = mins_per_dec;
        this.milliseconds_irl_to_decrement = secs_to_dec * 1000;
        this.timer = Clock.systemUTC();
        this.end_point = this.timer.millis() + this.milliseconds_irl_to_decrement;
    }

    /**
     * Updates the time, decrementing game time if necessary.
     * This method is called to passively drain time.
     */
    public void update() {
        if(this.end_point >= this.timer.millis()) return;
        this.minutes_remaining -= this.game_minutes_per_decrement;
        this.end_point = this.timer.millis() + this.milliseconds_irl_to_decrement;
    }

    @Override
    public void reset() {
        this.minutes_remaining = MINUTES_PER_DAY;
    }

    /**
     * Gets the number of bars to display in the progress bar on the HUD.
     *
     * @return The number of minutes remaining divided by MINS_IN_INTERVAL.
     */
    public int getIntervalsRemaining() {
        if(this.minutes_remaining < 1) return 0;

        return (int) Math.ceil((double) this.minutes_remaining / MINS_IN_INTERVAL);
    }

    /**
     * Gets the number of game minutes remaining.
     * Used by internal core function to check whether the game has ended
     *
     * @return The number of game minutes remaining.
     */
    public int getMinutesRemaining() {
        if(this.minutes_remaining < 1) return 0;

        return this.minutes_remaining;
    }
    @Override
    public ExitConditions isOk(int amount) {
        if(amount <= 0) return ExitConditions.TooLow;
        return ExitConditions.IsOk;
    }
    @Override
    public ResourceExitConditions tryActivityType(ActivityType type) {
        int cost_of_resource;
        switch(type) {
            case Study:
                cost_of_resource = TIME_PER_STUDY;
                break;
            case Recreation:
                cost_of_resource = TIME_PER_RECREATIONAL;
                break;
            case Food:
                cost_of_resource = TIME_PER_FOOD;
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
    @Override
    public void doActivity(ActivityType type) {
        switch(type) {
            case Study:
                this.minutes_remaining += TIME_PER_STUDY;
                return;
            case Recreation:
                this.minutes_remaining += TIME_PER_RECREATIONAL;
                return;
            case Food:
                this.minutes_remaining += TIME_PER_FOOD;
        }
    }
}