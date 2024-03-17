package com.waddle_ware.heslington_hustle.core;

public enum ResourceTypes
{
    Time,
    Energy;
    @Override
    public String toString()
    {
        switch (this)
        {
            case Time:
                return "Time";
            case Energy:
                return "Energy";
        }
        return "FAILED";
    }
}
