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

public class EndScreen implements Screen
{
    private final HeslingtonHustle game;
    private final Stage stage;
    private final CharSequence player_score;
    private final Texture to_render;
    private final FreeTypeFontGenerator font_gen;
    private final BitmapFont font;

    /**
     * Constructs new EndScreen
     * @param game reference to game
     * @param has_won Value passed in by core at end of game
     * @param score Value passed in by core at end of game
     */
    public EndScreen(HeslingtonHustle game, boolean has_won, int score)
    {
        this.game = game;
        if(has_won == true)
        {
            this.to_render = new Texture("WinScreen.png");
        }
        else
        {
            this.to_render = new Texture("LoseScreen.png");
        }
        this.font_gen = new FreeTypeFontGenerator(Gdx.files.internal("OETZTYP_.TTF"));
        this.font   = genFont();
        this.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.player_score = Integer.toString(score);
        stage = new Stage(new FitViewport(1920, 1080)); // Set virtual screen size to 16:9 aspect ratio
        Gdx.input.setInputProcessor(stage);
        initialiseMenu(); // Add menu elements
    }
    private BitmapFont genFont()
    {
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 150;
        param.borderColor = Color.BLACK;
        param.borderWidth = 6f;
        param.borderStraight = false;
        return font_gen.generateFont(param);
    }
    private void initialiseMenu() {
        VerticalGroup tutorial_group = new VerticalGroup();
        tutorial_group.setFillParent(true);
        tutorial_group.left().bottom();
        stage.addActor(tutorial_group);

//        TextButton.TextButtonStyle button_style = new TextButton.TextButtonStyle();
//        button_style.font = new BitmapFont(); // default font

//        // Play button
//        TextButton backButton = new TextButton("back", button_style);
//        backButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                switch (previous_screen) {
//                    case MenuScreen:
//                        game.setScreen(new MenuScreen(game));
//                        break;
//                    case PlayScreen:
//                        game.setScreen(new PlayScreen());
//                        break;
//                    default:
//                        game.setScreen(new MenuScreen(game));
//                }
//            }
//        });
//        tutorial_group.addActor(backButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.getBatch().begin();
        final float scaleX = stage.getViewport().getWorldWidth() / to_render.getWidth();
        final float scaleY = stage.getViewport().getWorldHeight() / to_render.getHeight();
        final float scale = Math.min(scaleX, scaleY);
        final float width = to_render.getWidth() * scale;
        final float height = to_render.getHeight() * scale;
        final float x = (stage.getViewport().getWorldWidth() - width) / 2;
        final float y = (stage.getViewport().getWorldHeight() - height) / 2;
        stage.getBatch().draw(to_render, x, y, width, height);
        this.font.draw(stage.getBatch(),player_score, 750, 300);
        stage.getBatch().end();

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
        to_render.dispose();
    }
}
