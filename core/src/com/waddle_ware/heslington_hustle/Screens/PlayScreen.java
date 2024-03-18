package com.waddle_ware.heslington_hustle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waddle_ware.heslington_hustle.ActivityLocation;
import com.waddle_ware.heslington_hustle.Avatar;
import com.waddle_ware.heslington_hustle.HUD;
import com.waddle_ware.heslington_hustle.HeslingtonHustle;
import com.waddle_ware.heslington_hustle.InteractionPopup;
import com.waddle_ware.heslington_hustle.core.ActivityType;
import com.waddle_ware.heslington_hustle.core.Core;
import com.waddle_ware.heslington_hustle.core.ExitConditions;
import com.waddle_ware.heslington_hustle.core.ResourceExitConditions;

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
    private final HeslingtonHustle game;

    private Core core;

    // Define activity locations
    private final ActivityLocation study_location = new ActivityLocation(130, 24, 20, "study"); // Bottom left building
    private final ActivityLocation recreation_location = new ActivityLocation(495, 144, 20, "feed the ducks"); // Ducks at pond
    private final ActivityLocation food_location = new ActivityLocation(570, 264, 20, "eat"); // Top right building
    private final ActivityLocation sleep_location = new ActivityLocation(250, 264, 20, "sleep"); // Top left building

    private InteractionPopup interaction_popup; // Add a field for the interaction pop-up
    private float popupX;
    private float popupY;

    /**
     * Constructs a new PlayScreen.
     *
     * @param game The game instance.
     */
    public PlayScreen(HeslingtonHustle game)
    {
        this.game = game;
    }

    /**
     * Called when this screen becomes the current screen.
     * Initialises camera, viewport, tile map, and player avatar.
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
        player.setPlayerLoc(260, 250);

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
        if(core.hasEnded()) {
            game.setScreen(new EndScreen(game, !core.hasPlayerFailed(), core.generateScore()));
        }
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

        checkInteractionProximity(); // Check for proximity and update interaction pop-ups

        // Render the pop-up message if it exists
        if (interaction_popup != null) {
            interaction_popup.render(map_renderer.getBatch(), popupX, popupY); // Adjust popupX and popupY as needed
        }

//        System.out.println("Player's current coordinates: X=" + player.getPlayerX() + ", Y=" + player.getPlayerY());
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
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            this.core.interactedWith(ActivityType.Study);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            this.core.interactedWith(ActivityType.Food);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            this.core.interactedWith(ActivityType.Sleep);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.core.interactedWith(ActivityType.Recreation);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            this.game.setScreen(new EndScreen(game, true, 2342));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            this.game.setScreen(new EndScreen(game, false, 7613));
        }
        // Toggle fullscreen when F11 is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
        // Interact when "E" is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            handleInteraction();
        }
    }

    /**
     * Handles player interaction with various activity locations based on their proximity to the player's position.
     * Checks if the player is within the interaction area of each activity location
     * If the player is within range, trigger the interaction with the activity and handle any outcome.
     */
    private void handleInteraction() {
        // Get players current position
        float playerX = player.getPlayerX();
        float playerY = player.getPlayerY();

        // Check for interaction with each activity location
        if (isPlayerWithinInteractionArea(playerX, playerY, study_location)) {
            final ResourceExitConditions exit_value = core.interactedWith(ActivityType.Study);
            if(exit_value.getConditions() == ExitConditions.IsOk) return;
            //visually output why the interaction failed
            //tmp:
            System.out.printf("%s%s\n",exit_value.getTypes().toString(),exit_value.getConditions().toString());
        }
        if (isPlayerWithinInteractionArea(playerX, playerY, recreation_location)) {
            final ResourceExitConditions exit_value = core.interactedWith(ActivityType.Recreation);
            if(exit_value.getConditions() == ExitConditions.IsOk) return;
            System.out.printf("%s%s\n",exit_value.getTypes().toString(),exit_value.getConditions().toString());
        }
        if (isPlayerWithinInteractionArea(playerX, playerY, food_location)) { // Food and sleep should not be able to fail, so they can remain unchecked
            core.interactedWith(ActivityType.Food);
            return;
        }
        if (isPlayerWithinInteractionArea(playerX, playerY, sleep_location)) {
            if(core.isLastDay()) {
                game.setScreen(new EndScreen(game, !core.hasPlayerFailed(), core.generateScore()));
            }
            else core.interactedWith(ActivityType.Sleep);
        }
    }

    /**
     * Checks the proximity of the player to various activity locations and updates the interaction pop-up message accordingly.
     * If the player is within range of an activity location, an interaction pop-up message is displayed.
     * If the player is not within range of any activity location, the interaction pop-up is hidden.
     */
    private void checkInteractionProximity() {
        // Get players current position
        float playerX = player.getPlayerX();
        float playerY = player.getPlayerY();

        // Check if the player is within range of an activity location
        if (isPlayerWithinInteractionArea(playerX, playerY, study_location)) {
            interaction_popup = new InteractionPopup("Press E to "+ study_location.getName());
            // set pop up above players location
            popupX = playerX;
            popupY = playerY + 50;
        } else if (isPlayerWithinInteractionArea(playerX, playerY, recreation_location)) {
            interaction_popup = new InteractionPopup("Press E to "+ recreation_location.getName());
            popupX = playerX;
            popupY = playerY + 50;
        } else if (isPlayerWithinInteractionArea(playerX, playerY, food_location)) {
            interaction_popup = new InteractionPopup("Press E to "+ food_location.getName());
            popupX = playerX;
            popupY = playerY + 50;
        } else if (isPlayerWithinInteractionArea(playerX, playerY, sleep_location)) {
            interaction_popup = new InteractionPopup("Press E to "+ sleep_location.getName());
            popupX = playerX;
            popupY = playerY + 50;
        } else {
            // Hide message if the player is out of range
            interaction_popup = null;
        }
    }

    /**
     * Toggles fullscreen mode of the application.
     * If the application is currently in fullscreen mode, it switches to windowed mode with the dimensions of the game world.
     * If the application is in windowed mode, it switches to fullscreen mode using the current display mode.
     */
    private void toggleFullscreen() {
        is_fullscreen = !is_fullscreen;

        if (is_fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode((int) world_width, (int) world_height);
        }
    }

    /**
     * Checks if the player is within the interaction area of a given activity location.
     *
     * @param playerX   The x-coordinate of the player's position.
     * @param playerY   The y-coordinate of the player's position.
     * @param location  The activity location to check for interaction area.
     * @return True if the player is within the interaction area of the activity location, false otherwise.
     */
    private boolean isPlayerWithinInteractionArea(float playerX, float playerY, ActivityLocation location) {
        // Calculate the squared distance between the player and the activity location
        float distance_squared = (playerX - location.getX()) * (playerX - location.getX())
                + (playerY - location.getY()) * (playerY - location.getY());
        // Compare squared distance with the square of the interaction radius of the location
        return distance_squared <= location.getRadius() * location.getRadius();
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
        hud.dispose();
    }
}
