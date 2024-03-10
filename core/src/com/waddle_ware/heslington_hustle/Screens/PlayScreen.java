package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The PlayScreen class represents the games screen where the gameplay is.
 * It implements the Screen interface and manages rendering and input handling.
 */
public class PlayScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tile_map;
    private OrthogonalTiledMapRenderer map_renderer;
    private boolean is_fullscreen = false;  // Track fullscreen state
    private Texture player_sprite; // Player image
    private float x, y; // Player position
    private float world_width;
    private float world_height;
    private float player_size;

    /**
     * Called when this screen becomes the current screen.
     * Initialises camera, viewport, tile map, and player sprite.
     */
    @Override
    public void show() {
        // Create camera and viewport
        camera = new OrthographicCamera();

        // Load tile Map
        tile_map = new TmxMapLoader().load("map.tmx"); // load tile map
        map_renderer = new OrthogonalTiledMapRenderer(tile_map);

        player_sprite = new Texture("player.png"); // load player sprite
        x = 0;
        y = 0;

        // Set target aspect ratio for tile map
        float target_aspect_ratio = 16f / 9f;

        // Calculate world dimensions
        int map_tile_width = tile_map.getProperties().get("width", Integer.class);
        int tile_width = tile_map.getProperties().get("tilewidth", Integer.class);
        world_width = map_tile_width * tile_width;
        world_height = world_width / target_aspect_ratio;

        // Set the viewport to use the whole screen with the desired aspect ratio
        viewport = new FitViewport(world_width, world_height, camera);

        // Center the camera on the tile map
        camera.position.set(world_width / 2f, world_height / 2f, 0);
        camera.update();

        // Adjust the viewport if needed to ensure the tile map fills the entire screen (for tile maps that are not 16:9)
        float aspect_ratio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        if (aspect_ratio > target_aspect_ratio) {
            float new_world_height = world_width * aspect_ratio;
            float y_offset = (new_world_height - world_height) / 2f;
            viewport.setWorldSize(world_width, new_world_height);
            camera.position.add(0, y_offset, 0);
            camera.update();
        }
    }

    /**
     * Called when screen should render itself.
     * Handles input, updates the camera, renders the tile map, and renders the player sprite on top.
     *
     * @param delta time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        handleInput(); // Call method to handle inputs

        // Update camera and viewport
        camera.update();
        map_renderer.setView(camera);

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map_renderer.render(); // Render tile map

        // Define player sprite dimensions
        player_size = 24f;

        // Render player sprite
        map_renderer.getBatch().begin();
        map_renderer.getBatch().draw(player_sprite, x, y, player_size, player_size); // Draw sprite in updated position with specified dimensions
        map_renderer.getBatch().end();
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
    }

    /**
     * Handles user input to move the sprite with wasd keys.
     * Checks boundaries to prevent the sprite from moving outside the game window.
     */
    private void handleInput() {
        float speed = 200f; // Player sprite speed

        float delta_X = 0;
        float delta_Y = 0;

        // Move player sprite based on key input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            delta_Y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            delta_Y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            delta_X -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            delta_X += 1;
        }

        // Normalise movement vector for diagonal movement
        if (delta_X != 0 && delta_Y != 0) {
            float length = (float) Math.sqrt(delta_X * delta_X + delta_Y * delta_Y);
            delta_X /= length;
            delta_Y /= length;
        }

        // Update player position
        x += delta_X * Gdx.graphics.getDeltaTime() * speed;
        y += delta_Y * Gdx.graphics.getDeltaTime() * speed;

        // Ensure player stays within the game window boundaries
        x = MathUtils.clamp(x, 0, world_width - player_size);
        y = MathUtils.clamp(y, 0, world_height - player_size);

        // Toggle fullscreen when F11 is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    private void toggleFullscreen() {
        is_fullscreen = !is_fullscreen;

        if (is_fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode((int) world_width, (int) world_height);
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
        tile_map.dispose();
        map_renderer.dispose();
        player_sprite.dispose();
    }
}
