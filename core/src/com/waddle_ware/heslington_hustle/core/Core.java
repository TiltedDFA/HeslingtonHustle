package com.waddle_ware.heslington_hustle.core;

/**
 * The Core class represents the core functionality of the game, managing game state, interactions, and scoring.
 */
public class Core {
    // The constants below define the values for the impact that the individual components will have on the score.
    private static final int MEAL_SCORE_VALUE = 100;
    private static final int RELAX_SCORE_VALUE = 100;
    private static final int STUDY_SCORE_VALUE = 100;
    private static final int MEAL_SCORE_PENALTY = -100;
    private static final int RELAX_SCORE_PENALTY = -100;
    private static final int STUDY_TOO_MUCH_PENALTY = -100;
    private static final int TOO_MUCH_STUDY_THRESHOLD = 3;

    private static final int MAX_NUMBER_OF_DAYS = 7;

    // Member variables
    private final Energy energy;
    private int day;
    private final Time time;

    private final int[] study_count;
    private final int[] relax_count;
    private final int[] meal_count;

    /**
     * Constructs a new Core instance.
     * Initialises energy, time, and activity count arrays.
     */
    public Core() {
        this.energy = new Energy(4);
        this.time = new Time(15,3);
        this.study_count = new int[7];
        this.relax_count = new int[7];
        this.meal_count = new int[7];
    }

    /**
     * This method should be called in the main update loop in order
     * to keep the game state up to date. Currently, the only thing that
     * needs updating is time (to see if enough irl time has passed
     * to decrease the remaining time).
     */
    public void update() {
        this.time.update();
    }

    /**
     * This function is the main way for the GUI to interact and manipulate
     * the game state.
     * It should be called whenever a person tries to interact with a building.
     * It will check if the interaction is legal (there is enough energy and time).
     *
     * @param type The type of the interaction.
     * @return Resource exit condition. If it is successful, returns (Null, IsOk),
     * else it returns the ResourceType and the type of fault (too high / low).
     */
    public ResourceExitConditions interactedWith(ActivityType type) {
        if(type == ActivityType.Sleep) {
            incrementDay();
            return new ResourceExitConditions(null, ExitConditions.IsOk);
        }
        //check if we can do activity
        final ResourceExitConditions energy = this.energy.tryActivityType(type);
        final ResourceExitConditions time = this.time.tryActivityType(type);
        if(energy.getConditions() != ExitConditions.IsOk) return energy;
        if(time.getConditions() != ExitConditions.IsOk) return time;

        //do activity
        this.time.doActivity(type);
        this.energy.doActivity(type);

        //update activity tracking data
        switch (type) {
            case Study:
                this.study_count[this.day] += 1;
                break;
            case Recreation:
                this.relax_count[this.day] += 1;
                break;
            case Food:
                this.meal_count[this.day] += 1;
                break;
            default:
                break;
        }
        return new ResourceExitConditions(null, ExitConditions.IsOk);
    }

    /**
     * Checks whether it's the last day of the game.
     *
     * @return true if it's the last day, false otherwise.
     */
    public boolean isLastDay() {
        return this.day >= (MAX_NUMBER_OF_DAYS - 1);
    }

    /**
     * Checks whether the game has ended. (Intended to be used to indicate a need to change from the play screen).
     *
     * @return true if the game has ended, false otherwise.
     */
    public boolean hasEnded() {
         return isLastDay() && this.time.getMinutesRemaining() == 0;
    }

    /**
     * Changes to the next day.
     * Throws an exception if the maximum number of days is exceeded.
     */
    public void incrementDay() {
        //makes sure that we can't go over the max number
        //of days
        if(this.day > (MAX_NUMBER_OF_DAYS - 1))
            throw new RuntimeException("Attempted to increment day past its max");
        ++this.day;
        this.time.reset();
        this.energy.reset();
    }

    /**
     * Checks whether the player has hit a fail condition
     * Intended to be used after game has ended to determine
     * whether to display a fail or win screen.
     * This should only be called once the game has ended.
     * It will throw an exception if called before.
     *
     * @return true/false based on whether the play has failed
     */
    public boolean hasPlayerFailed() {
        if(!isLastDay())
            throw new RuntimeException("hasPlayerFailed has been called before the game has ended");

        // fail conditions are missed studying for 2 days
        for (final int i : this.meal_count) {
            if(i == 0) return true;
        }
        for (final int i : this.relax_count) {
            if(i == 0) return true;
        }
        int studied_zero_times_per_day_count = 0;
        for (final int i : this.study_count) {
            if(i == 0) ++studied_zero_times_per_day_count;
        }
        if (studied_zero_times_per_day_count >= 2) return true;
        return false;
    }

    /**
     * This function generate the player's score based on their
     * tracked metrics and the value of each metric as specified
     * by their respective constants.
     * This should only be called once the game has ended.
     * It will throw an exception if called before.
     *
     * @return The total score that the player has achieved
     */
    public int generateScore() {
        if(!isLastDay())
            throw new RuntimeException("generateScore has been called before the game has ended");
        int score = 0;
        for (int i = 0; i < 7; ++i) {

            if(this.meal_count[i] == 0)
                score += MEAL_SCORE_PENALTY;
            else
                score += this.meal_count[i]  * MEAL_SCORE_VALUE;

            if(this.relax_count[i] == 0)
                score += RELAX_SCORE_PENALTY;
            else
                score += this.relax_count[i]  * RELAX_SCORE_VALUE;

            if(this.study_count[i] >= TOO_MUCH_STUDY_THRESHOLD)
                score += STUDY_TOO_MUCH_PENALTY;
            else
                score += this.study_count[i]  * STUDY_SCORE_VALUE;

        }
        return score;
    }

    /**
     * Returns the day. NOT zero indexed
     *
     * @return current day
     */
    public int getCurrentDay() {
        return this.day + 1;
    }

    /**
     * Returns the energy limit.
     *
     * @return The energy limit.
     */
    public int getEnergyLimit() {
        return this.energy.getLimit();
    }

    /**
     * Returns the current energy.
     *
     * @return The current energy.
     */
    public int getCurrentEnergy() {
        return this.energy.getCurrentEnergy();
    }

    /**
     * Returns the time limit.
     *
     * @return The time limit.
     */
    public int getTimeLimit() {
        return Time.MINUTES_PER_DAY;
    }

    /**
     * Returns the remaining time.
     *
     * @return The remaining time.
     */
    public int getTimeRemaining() {
        return this.time.getMinutesRemaining();
    }

    /**
     * Returns the number of times studied today.
     *
     * @return The number of times studied today.
     */
    public int getTimesStudiedToday() {
        return study_count[this.day];
    }

    /**
     * Returns the number of times eaten today.
     *
     * @return The number of times eaten today.
     */
    public int getTimesEatenToday() {
        return meal_count[this.day];
    }
}
