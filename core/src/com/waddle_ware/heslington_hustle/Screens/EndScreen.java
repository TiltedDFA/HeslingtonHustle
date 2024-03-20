package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.waddle_ware.heslington_hustle.HeslingtonHustle;

/**
 * The EndScreen class represents the screen displayed at the end of the game.
 * It displays either a win or lose screen based on the game outcome.
 */
public class EndScreen implements Screen {
    private final HeslingtonHustle game;
    private final Stage stage;
    private final CharSequence player_score;
    private final Texture to_render;
    private final FreeTypeFontGenerator font_gen;
    private final BitmapFont font;

    /**
     * Constructs a new EndScreen.
     *
     * @param game      The game instance.
     * @param has_won   Boolean value indicating whether the player has won the game.
     * @param score     The player's score at the end of the game.
     */
    public EndScreen(HeslingtonHustle game, boolean has_won, int score) {
        this.game = game;
        if(has_won == true) {
            this.to_render = new Texture("WinScreen.png");
        }
        else {
            this.to_render = new Texture("LoseScreen.png");
        }
        this.font_gen = new FreeTypeFontGenerator(Gdx.files.internal("OETZTYP_.TTF"));
        this.font   = genFont();
        this.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.player_score = score < 0 ? "0" : Integer.toString(score);
        this.stage = new Stage(new FitViewport(1920, 1080)); // Set virtual screen size to 16:9 aspect ratio
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * Generates a custom font for displaying the player's score on the end screen.
     *
     * @return The generated BitmapFont object with custom font settings.
     */
    private BitmapFont genFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 150;
        param.borderColor = Color.BLACK;
        param.borderWidth = 6f;
        param.borderStraight = false;
        return font_gen.generateFont(param);
    }

    @Override
    public void show() {
    }

    /**
     * Called when this screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        this.stage.getBatch().begin();
        final float scaleX = this.stage.getViewport().getWorldWidth() / to_render.getWidth();
        final float scaleY = this.stage.getViewport().getWorldHeight() / to_render.getHeight();
        final float scale = Math.min(scaleX, scaleY);
        final float width = this.to_render.getWidth() * scale;
        final float height = this.to_render.getHeight() * scale;
        final float x = (this.stage.getViewport().getWorldWidth() - width) / 2;
        final float y = (this.stage.getViewport().getWorldHeight() - height) / 2;
        this.stage.getBatch().draw(this.to_render, x, y, width, height);
        this.font.draw(this.stage.getBatch(),this.player_score, 750, 300);
        this.stage.getBatch().end();

        this.stage.draw();
    }

    /**
     * Called when the screen is resized.
     *
     * @param width  The new width in pixels.
     * @param height The new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * Disposes of this screen's resources.
     * This method is called when this screen is no longer needed.
     */
    @Override
    public void dispose() {
        this.stage.dispose();
        this.to_render.dispose();
    }
}
