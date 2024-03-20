package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * The InteractionPopup class represents a pop-up message displayed when a player is within range of an interaction.
 * It renders the message using a specified font and displays it at a given position on the screen.
 */
public class InteractionPopup {
    private final BitmapFont font;
    private final String message;

    /**
     * Constructs a new InteractionPopup object with specified message.
     *
     * @param message The message to be displayed in the pop-up.
     */
    public InteractionPopup(String message) {
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BebasNeue-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1.5f;
        parameter.borderStraight = false;

        // Generate font and dispose generator
        this.font = generator.generateFont(parameter);
        generator.dispose();
        this.message = message;
    }

    /**
     * Renders the pop-up message at the specified position on the screen.
     *
     * @param batch The batch used for rendering.
     * @param x     The x-coordinate of the pop-up position.
     * @param y     The y-coordinate of the pop-up position.
     */
    public void render(Batch batch, float x, float y) {
        batch.begin();
        this.font.draw(batch, this.message, x, y);
        batch.end();
    }

    /**
     * Disposes of the font used for rendering the pop-up message.
     */
    public void dispose() {
        this.font.dispose();
    }
}
