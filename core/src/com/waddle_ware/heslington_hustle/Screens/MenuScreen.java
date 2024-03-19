package com.waddle_ware.heslington_hustle.Screens;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private final Texture background;

    /**
     * Constructs a new MenuScreen.
     *
     * @param game The game instance.
     */
    public MenuScreen(HeslingtonHustle game) {
        this.game = game;
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        this.background  = new Texture("MenuScreen.png");
        initialiseMenu(); // Add menu elements
    }
    private ImageButton.ImageButtonStyle createTexRegDraw(String path)
    {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable( new TextureRegion(new Texture(path)));
        style.imageUp.setMinWidth(475);
        style.imageUp.setMinHeight(125);
        return style;
    }

    /**
     * Initialises menu elements, such as buttons and their listeners.
     */
    private void initialiseMenu() {
        VerticalGroup menu_group = new VerticalGroup();
        menu_group.setFillParent(true);
        menu_group.center(); // centre align vertically
        menu_group.align(Align.bottom);
        stage.addActor(menu_group);

        TextButtonStyle button_style = new TextButtonStyle();
        button_style.font = new BitmapFont(); // default font

        // Play button
        ImageButton playButton = new ImageButton(createTexRegDraw("PlayButton.png"));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });

        ImageButton tutorialButton = new ImageButton(createTexRegDraw("TutorialButton.png"));
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game, ScreenId.MenuScreen));
            }
        });
        // Exit button
        ImageButton exitButton = new ImageButton(createTexRegDraw("ExitButton.png"));
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
        float scaleX = stage.getViewport().getWorldWidth() / background.getWidth();
        float scaleY = stage.getViewport().getWorldHeight() / background.getHeight();
        float scale = Math.min(scaleX, scaleY);
        float width = background.getWidth() * scale;
        float height = background.getHeight() * scale;
        float x = (stage.getViewport().getWorldWidth() - width) / 2;
        float y = (stage.getViewport().getWorldHeight() - height) / 2;
        stage.getBatch().draw(background, x, y, width, height);
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
        background.dispose();
    }
}
