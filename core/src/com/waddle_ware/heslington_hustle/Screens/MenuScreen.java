package com.waddle_ware.heslington_hustle.Screens;
import com.waddle_ware.heslington_hustle.HeslingtonHustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The MenuScreen class represents the screen where the game menu is displayed.
 * It implements the Screen interface and handles user input for menu navigation.
 */
public class MenuScreen implements Screen {
    private final HeslingtonHustle game;
    private final Stage stage;

    /**
     * Constructs a new MenuScreen.
     *
     * @param game The game instance.
     */
    public MenuScreen(HeslingtonHustle game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        initialiseMenu(); // Add menu elements
    }

    /**
     * Initialises menu elements, such as buttons and their listeners.
     */
    private void initialiseMenu() {
        VerticalGroup menu_group = new VerticalGroup();
        menu_group.setFillParent(true);
        menu_group.center(); // centre align vertically
        stage.addActor(menu_group);

        TextButtonStyle button_style = new TextButtonStyle();
        button_style.font = new BitmapFont(); // default font

        // Play button
        TextButton playButton = new TextButton("Play", button_style);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });
        TextButton tutorialButton = new TextButton("Tutorial", button_style);
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game, ScreenId.MenuScreen));
            }
        });
        // Exit button
        TextButton exitButton = new TextButton("Exit", button_style);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menu_group.addActor(playButton);
        menu_group.addActor(tutorialButton);
        menu_group.addActor(exitButton);
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
