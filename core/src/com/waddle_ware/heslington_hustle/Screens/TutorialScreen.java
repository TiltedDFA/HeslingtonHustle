package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.waddle_ware.heslington_hustle.HeslingtonHustle;

public class TutorialScreen implements Screen
{
    private final HeslingtonHustle game;
    private final Stage stage;
    /**
     * This is to be used to get back to the screen that called
     * the tutorial screen as it should be able to be called
     * from either play screen or menu screen
     */
    private Screen previous_screen;
    /**
     * Constructs a new MenuScreen.
     *
     * @param game The game instance.
     */
    public TutorialScreen(HeslingtonHustle game, Screen previous_screen) {
        this.previous_screen = previous_screen;
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        initialiseMenu(); // Add menu elements
    }

    /**
     * Initialises menu elements, such as buttons and their listeners.
     */
    private void initialiseMenu() {
        VerticalGroup tutorial_group = new VerticalGroup();
        tutorial_group.setFillParent(true);
        tutorial_group.center(); // centre align vertically
        stage.addActor(tutorial_group);

        TextButton.TextButtonStyle button_style = new TextButton.TextButtonStyle();
        button_style.font = new BitmapFont(); // default font

        // Play button
        TextButton backButton = new TextButton("back", button_style);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(previous_screen);
            }
        });
        tutorial_group.addActor(backButton);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
