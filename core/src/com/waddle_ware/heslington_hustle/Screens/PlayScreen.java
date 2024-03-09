package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The PlayScreen class represents the games screen where the gameplay is.
 * It implements the Screen interface and manages rendering and input handling.
 */
public class PlayScreen implements Screen {
    private SpriteBatch batch;
    private Texture img;
    private float x, y; // Sprite position
    private OrthographicCamera camera;
    private Viewport viewport;

    //  variables for sprite and world dimensions
    private float spriteWidth;
    private float spriteHeight;
    private float worldWidth;
    private float worldHeight;

    /**
     * Called when this screen becomes the current screen.
     * Initialises sprite, camera, and viewport.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        x = 0;
        y = 0;

        // Create camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        // Initialize sprite and world dimensions
        spriteWidth = img.getWidth();
        spriteHeight = img.getHeight();
        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();

        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        camera.update();
    }

    /**
     * Called when screen should render itself.
     * Handles input and updates the camera before rendering the sprite.
     *
     * @param delta time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        handleInput(); // Call method to handle inputs

        // Update camera and viewport
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, x, y); // Draw sprite in updated position
        batch.end();
    }

    /** Called when the window is resized.
     * Updates the viewport and ensures the sprite stays within the new window boundaries.
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        // Update viewport when the window is resized
        viewport.update(width, height);

        // Update world dimensions
        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();

        // Ensure sprite stays within the new window boundaries
        if (x + spriteWidth > worldWidth) {
            x = worldWidth - spriteWidth;
        }
        if (y + spriteHeight > worldHeight) {
            y = worldHeight - spriteHeight;
        }
    }

    /**
     * Handles user input to move the sprite with wasd keys.
     * Checks boundaries to prevent the sprite from moving outside the game window.
     */
    private void handleInput() {
        float speed = 200f; // Sprite speed

        // Move sprite based on key input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (y + spriteHeight + Gdx.graphics.getDeltaTime() * speed <= worldHeight) { // Check move is within boundary
                y += Gdx.graphics.getDeltaTime() * speed;
            } else {
                y = worldHeight - spriteHeight; // set position to boundary
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (y - Gdx.graphics.getDeltaTime() * speed >= 0) {
                y -= Gdx.graphics.getDeltaTime() * speed;
            } else {
                y = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x - Gdx.graphics.getDeltaTime() * speed >= 0) {
                x -= Gdx.graphics.getDeltaTime() * speed;
            } else {
                x = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x + spriteWidth + Gdx.graphics.getDeltaTime() * speed <= worldWidth) {
                x += Gdx.graphics.getDeltaTime() * speed;
            } else {
                x = worldWidth - spriteWidth;
            }
        }
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

    /** Called when the application is destroyed. Preceded by a call to {@link #pause()}. */
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
