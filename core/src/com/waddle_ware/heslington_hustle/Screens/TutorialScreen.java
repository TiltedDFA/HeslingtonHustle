package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.waddle_ware.heslington_hustle.HeslingtonHustle;

/**
 * This class represents the games tutorial screen.
 * It displays controls and instructions on how to play the game.
 */
public class TutorialScreen implements Screen {
    private final HeslingtonHustle game;
    private final Stage stage;
    private final ScreenId previous_screen;
    private final Texture tutorial_img;

    /**
     * Constructs a new TutorialScreen.
     *
     * @param game             The game instance.
     * @param previous_screen The screen to return to upon pressing the back button.
     */
    public TutorialScreen(HeslingtonHustle game, ScreenId previous_screen) {
        this.previous_screen = previous_screen;
        this.game = game;
        tutorial_img = new Texture("tutorial.png");
        stage = new Stage(new FitViewport(1920, 1080)); // Set virtual screen size to 16:9 aspect ratio
        Gdx.input.setInputProcessor(stage);
        initialiseMenu(); // Add menu elements
    }

    /**
     * Initialises the tutorial screen with associated UI elements.
     */
    private void initialiseMenu() {
        VerticalGroup tutorial_group = new VerticalGroup();
        tutorial_group.setFillParent(true);
        tutorial_group.left().bottom();
        stage.addActor(tutorial_group);

        TextButton.TextButtonStyle button_style = new TextButton.TextButtonStyle();
        button_style.font = new BitmapFont(); // default font

        // Play button
        TextButton backButton = new TextButton("back", button_style);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (previous_screen) {
                    case MenuScreen:
                        game.setScreen(new MenuScreen(game));
                        break;
                    case PlayScreen:
                        game.setScreen(new PlayScreen(game));
                        break;
                    default:
                        game.setScreen(new MenuScreen(game));
                }
            }
        });
        tutorial_group.addActor(backButton);
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

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.getBatch().begin();
        float scaleX = stage.getViewport().getWorldWidth() / tutorial_img.getWidth();
        float scaleY = stage.getViewport().getWorldHeight() / tutorial_img.getHeight();
        float scale = Math.min(scaleX, scaleY);
        float width = tutorial_img.getWidth() * scale;
        float height = tutorial_img.getHeight() * scale;
        float x = (stage.getViewport().getWorldWidth() - width) / 2;
        float y = (stage.getViewport().getWorldHeight() - height) / 2;
        stage.getBatch().draw(tutorial_img, x, y, width, height);
        stage.getBatch().end();

        stage.draw();
    }

    /**
     * Called when the screen is resized.
     *
     * @param width  The new width in pixels.
     * @param height The new height in pixels.
     */
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

    /**
     * Disposes of this screen's resources.
     * This method is called when this screen is no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose();
        tutorial_img.dispose();
    }
}
