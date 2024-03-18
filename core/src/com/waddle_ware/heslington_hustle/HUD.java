package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.waddle_ware.heslington_hustle.core.Core;

/**
 * The HUD  class displays game information on the screen, including:
 * - Energy level
 * - Time remaining
 * - Number of times studied today
 * - Number of times eaten today
 * - Current day
 */
public class HUD {
    private final BitmapFont font;
    CharSequence energy;
    CharSequence time;
    CharSequence studied;
    CharSequence eaten;
    CharSequence current_day;
    FreeTypeFontGenerator font_gen;

    /**
     * Constructs a HUD object.
     *
     * @param c The Core object providing necessary game data.
     */
    public HUD(Core c) {
        this.font_gen = new FreeTypeFontGenerator(Gdx.files.internal("BebasNeue-Regular.ttf"));
        this.font   = genFont();
        this.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.energy = String.format("Energy: %d / %d", c.getCurrentEnergy(), c.getEnergyLimit());
        this.time   = String.format("Time: %d mins / %d mins", c.getTimeRemaining(), c.getTimeLimit());
        this.studied = String.format("Studied: %d", c.getTimesStudiedToday());
        this.eaten = String.format("Eaten: %d", c.getTimesEatenToday());
        this.current_day = String.format("Day %d", c.getCurrentDay());
    }

    /**
     * Generates a BitmapFont object for rendering text.
     *
     * @return The generated BitmapFont object.
     */
    private BitmapFont genFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 15;
        param.borderColor = Color.BLACK;
        param.borderWidth = 1.5f;
        param.borderStraight = false;
        return font_gen.generateFont(param);
    }

    /**
     * Renders HUD on the screen.
     *
     * @param batch The Batch object used for rendering.
     */
    public void render(Batch batch) {
        this.font.draw(batch,this.energy,       580, 400);
        this.font.draw(batch,this.time,         580, 380);
        this.font.draw(batch,this.studied,      580, 360);
        this.font.draw(batch,this.eaten,        580, 340);
        this.font.draw(batch,this.current_day,  580, 320);
    }

    /**
     * Updates the HUD with new game data.
     *
     * @param c The Core object providing updated game data.
     */
    public void update(Core c) {
        this.energy = String.format("Energy: %d / %d", c.getCurrentEnergy(), c.getEnergyLimit());
        this.time   = String.format("Time: %d mins / %d mins", c.getTimeRemaining(), c.getTimeLimit());
        this.studied = String.format("Studied: %d", c.getTimesStudiedToday());
        this.eaten = String.format("Eaten: %d", c.getTimesEatenToday());
        this.current_day = String.format("Day %d", c.getCurrentDay());
    }

    /**
     * Disposes resources used by the HUD.
     */
    public void dispose() {
        this.font.dispose();
        this.font_gen.dispose();
    }
}
