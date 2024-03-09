package com.waddle_ware.heslington_hustle.core;

public class Core
{
    private static int MealScoreValue = 100;
    private static int RelaxScoreValue = 100;
    private static int StudyScoreValue = 100;
    private Energy energy;
    private int day;
    private Time time;

    private int study_count[];
    private int relax_count[];
    private int meal_count[];

    public Core()
    {
        this.energy = new Energy();
        this.time = new Time(15,3);
        this.study_count = new int[7];
        this.relax_count = new int[7];
        this.meal_count = new int[7];
    }
    void update()
    {
        this.time.update();
    }
    ResourceExitConditions interactedWith(ActivityType type)
    {
        //check if we can do activity
        final ResourceExitConditions energy = this.energy.tryActivityType(type);
        final ResourceExitConditions time = this.time.tryActivityType(type);
        if(energy.GetConditions() != ExitConditions.IsOk) return energy;
        if(time.GetConditions() != ExitConditions.IsOk) return time;

        //do activity
        this.time.doActivity(type);
        this.energy.doActivity(type);

        //update activity tracking data
        switch (type)
        {
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
    public boolean hasEnded()
    {
        return this.day >= 6 && this.time.getMinutesRemaining() == 0;
    }
    public void incrementDay()
    {
        ++this.day;
        this.time.reset();
        this.energy.reset();
    }
    public boolean hasPlayerFailed()
    {
        // fail conditions are missed studying for 2 days
        for (final int i : this.meal_count)
        {
            if(i == 0) return true;
        }
        for (final int i : this.relax_count)
        {
            if(i == 0) return true;
        }
        int studied_zero_times_per_day_count = 0;
        for (final int i : this.study_count)
        {
            if(i == 0) ++studied_zero_times_per_day_count;
        }
        if (studied_zero_times_per_day_count >= 2) return true;
        return false;
    }
    public int generateScore()
    {
        int score = 0;
        for (int i = 0; i < 7; i++) 
        {
            score += this.meal_count[i]  * MealScoreValue;
            score += this.relax_count[i] * RelaxScoreValue;
            score += this.study_count[i] * StudyScoreValue;
        }
        return score;
    }
}
