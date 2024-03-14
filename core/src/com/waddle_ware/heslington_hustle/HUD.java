package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waddle_ware.heslington_hustle.core.Core;

public class HUD
{

    private BitmapFont font;
    CharSequence energy;
    CharSequence time;
    CharSequence studied;
    CharSequence eaten;
    CharSequence current_day;
    public HUD(Viewport view, Core c)
    {
        this.font   = new BitmapFont();

        this.energy = String.format("Energy: %d / %d", c.getCurrentEnergy(), c.getEnergyLimit());
        this.time   = String.format("Time: %d mins / %d mins", c.getTimeRemaining(), c.getTimeLimit());
        this.studied = String.format("Studied: %d", c.getTimesStudiedToday());
        this.eaten = String.format("Eaten: %d", c.getTimesEatenToday());
        this.current_day = String.format("Day %d", c.getCurrentDay());
    }
    public void render(Batch batch)
    {
        this.font.draw(batch,this.energy,       580, 400);
        this.font.draw(batch,this.time,         580, 380);
        this.font.draw(batch,this.studied,      580, 360);
        this.font.draw(batch,this.eaten,        580, 340);
        this.font.draw(batch,this.current_day,  580, 320);
    }

    public void update(Core c)
    {
        this.energy = String.format("Energy: %d / %d", c.getCurrentEnergy(), c.getEnergyLimit());
        this.time   = String.format("Time: %d mins / %d mins", c.getTimeRemaining(), c.getTimeLimit());
        this.studied = String.format("Studied: %d", c.getTimesStudiedToday());
        this.eaten = String.format("Eaten: %d", c.getTimesEatenToday());
        this.current_day = String.format("Day %d", c.getCurrentDay());
    }
}
