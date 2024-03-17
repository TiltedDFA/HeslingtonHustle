package com.waddle_ware.heslington_hustle.core;

/**
 * This is to be used to identify an activities type.
 */
public enum ActivityType
{
    Study,
    Recreation,
    Food,
    Sleep;
    @Override
    public String toString()
    {
        switch (this)
        {
            case Recreation:
                return "Recreation";
            case Study:
                return "Study";
            case Food:
                return "Food";
            case Sleep:
                return "Sleep";
        }
        return "FAILED";
    }
}
