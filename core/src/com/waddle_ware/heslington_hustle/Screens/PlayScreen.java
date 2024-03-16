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
import com.waddle_ware.heslington_hustle.ActivityLocation;
import com.waddle_ware.heslington_hustle.Avatar;
import com.waddle_ware.heslington_hustle.HUD;
import com.waddle_ware.heslington_hustle.core.ActivityType;
import com.waddle_ware.heslington_hustle.core.Core;

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

    private Avatar player;
    private float world_width;
    private float world_height;
    private HUD hud;

    private Core core;

    // Define activity locations
    private final ActivityLocation study_location = new ActivityLocation(130, 24, 20, "Study"); // Bottom left building
    private final ActivityLocation recreation_location = new ActivityLocation(495, 144, 20, "Recreation"); // Ducks at pond
    private final ActivityLocation food_location = new ActivityLocation(570, 264, 20, "Food"); // Top right building
    private final ActivityLocation sleep_location = new ActivityLocation(250, 264, 20, "Sleep"); // Top left building


    /**
     * Called when this screen becomes the current screen.
     * Initialises camera, viewport, tile map, and player sprite.
     */
    @Override
    public void show() {
        // Create camera and viewport
        camera = new OrthographicCamera();

        core = new Core();
        // Load tile Map
        tile_map = new TmxMapLoader().load("map.tmx"); // load tile map
        map_renderer = new OrthogonalTiledMapRenderer(tile_map);

        // Set target aspect ratio for tile map
        float target_aspect_ratio = 16f / 9f;

        // Calculate world dimensions
        int map_tile_width = tile_map.getProperties().get("width", Integer.class);
        int tile_width = tile_map.getProperties().get("tilewidth", Integer.class);
        world_width = map_tile_width * tile_width;
        world_height = world_width / target_aspect_ratio;
        System.out.printf("width: %f, height: %f", world_width,world_height);
        player = new Avatar(0, 0, world_height, world_width);
        // Set the viewport to use the whole screen with the desired aspect ratio
        viewport = new FitViewport(world_width, world_height, camera);
        hud = new HUD(core);
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
        player.handleInput();

        // Update camera and viewport
        camera.update();
        map_renderer.setView(camera);
        hud.update(core);
        player.update(tile_map);
        core.update();

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map_renderer.render(); // Render tile map

        // Render player sprite
        map_renderer.getBatch().begin();
        player.render(map_renderer);// Draw sprite in updated position with specified dimensions
        hud.render(map_renderer.getBatch());
        map_renderer.getBatch().end();

        System.out.println("Player's current coordinates: X=" + player.getPlayerX() + ", Y=" + player.getPlayerY());
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
        //Test for core
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.O))
        {
            this.core.interactedWith(ActivityType.Study);
        }
        // Toggle fullscreen when F11 is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
        // Interact when "E" is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            float playerX = player.getPlayerX();
            float playerY = player.getPlayerY();

            // Check for interaction with each activity location
            if (isPlayerWithinInteractionArea(playerX, playerY, study_location)) {
                core.interactedWith(ActivityType.Study);
            } else if (isPlayerWithinInteractionArea(playerX, playerY, recreation_location)) {
                core.interactedWith(ActivityType.Recreation);
            } else if (isPlayerWithinInteractionArea(playerX, playerY, food_location)) {
                core.interactedWith(ActivityType.Food);
            } else if (isPlayerWithinInteractionArea(playerX, playerY, sleep_location)) {
                core.interactedWith(ActivityType.Sleep);
            }
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

    private boolean isPlayerWithinInteractionArea(float playerX, float playerY, ActivityLocation location) {
        float distanceSquared = (playerX - location.getX()) * (playerX - location.getX())
                + (playerY - location.getY()) * (playerY - location.getY());
        return distanceSquared <= location.getRadius() * location.getRadius();
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
        player.dispose();
    }
}
