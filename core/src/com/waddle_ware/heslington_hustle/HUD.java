package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.waddle_ware.heslington_hustle.core.Core;

public class HUD
{

    private BitmapFont font;
    CharSequence energy;
    CharSequence time;
    CharSequence studied;
    CharSequence eaten;
    CharSequence current_day;
    FreeTypeFontGenerator font_gen;

    public HUD(Core c)
    {
        this.font_gen = new FreeTypeFontGenerator(Gdx.files.internal("BebasNeue-Regular.ttf"));
        this.font   = genFont();
        this.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.energy = String.format("Energy: %d / %d", c.getCurrentEnergy(), c.getEnergyLimit());
        this.time   = String.format("Time: %d mins / %d mins", c.getTimeRemaining(), c.getTimeLimit());
        this.studied = String.format("Studied: %d", c.getTimesStudiedToday());
        this.eaten = String.format("Eaten: %d", c.getTimesEatenToday());
        this.current_day = String.format("Day %d", c.getCurrentDay());
    }
    private BitmapFont genFont()
    {
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 15;
        param.borderColor = Color.BLACK;
        param.borderWidth = 1.5f;
        param.borderStraight = false;
        return font_gen.generateFont(param);
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
    public void dispose()
    {

    }
}
