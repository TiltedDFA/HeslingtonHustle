package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.waddle_ware.heslington_hustle.PlayerAnimator.state_time;

/**
 * The Avatar class represents the player's character in the game.
 * It manages the player's movement, collision detection, and rendering.
 */
public class Avatar {
    private final float player_size;
    private float player_x, player_y;
    private final Vector2 velocity;

    private final float world_height;
    private final float world_width;
    private final static float ACCELERATION = 10f;
    //the player's speed will be multiplied by this so
    //the player's current velocity will be reduced by
    //1 - friction every frame/update cycle
    private final static float FRICTION = 0.8f;

    private final static float MAX_VELOCITY = 200f;

    /**
     * Constructs an Avatar object.
     *
     * @param plyr_x       The initial x-coordinate of the player.
     * @param plyr_y       The initial y-coordinate of the player.
     * @param world_height The height of the game world.
     * @param world_width  The width of the game world.
     */
    public Avatar(float plyr_x, float plyr_y, float world_height, float world_width) {
        this.player_x = plyr_x;
        this.player_y = plyr_y;
        this.velocity = new Vector2(0,0);
        this.player_size = 24f;
        this.world_height = world_height;
        this.world_width = world_width;
    }

    /**
     * Updates player position and checks for collisions with tiles on the game map.
     *
     * @param tile_map The TiledMap representing the game map.
     */
    public void update(TiledMap tile_map) {
        final float oldX = this.player_x;
        final float oldY = this.player_y;

        this.velocity.clamp(0, MAX_VELOCITY);
        movePlayer(this.velocity.x * Gdx.graphics.getDeltaTime(), this.velocity.y * Gdx.graphics.getDeltaTime());

        if(isTilesBlocked(tile_map, this.player_x, this.player_y, this.player_size)) {
            onCollision(tile_map, oldX, oldY);
        }
    }

    /**
     * Handles collisions between the player and the game map.
     *
     * @param tile_map The TiledMap representing the game map.
     * @param oldX     The previous x-coordinate of the player.
     * @param oldY     The previous y-coordinate of the player.
     */
    public void onCollision(TiledMap tile_map, final float oldX, final float oldY) {
        float newX = this.player_x;
        float newY = this.player_y;

        // Check if the player is stuck horizontally
        if (isTilesBlocked(tile_map, newX, oldY, this.player_size)) {
            newX = oldX;
            this.velocity.x = 0; // Reset horizontal velocity
        }

        // Check if the player is stuck vertically
        if (isTilesBlocked(tile_map, oldX, newY, this.player_size)) {
            newY = oldY;
            this.velocity.y = 0; // Reset vertical velocity
        }

        // Update player position
        this.player_x = newX;
        this.player_y = newY;
    }

    /**
     * Checks if the player collides with any blocked tiles in the game map.
     *
     * @param tile_map The TiledMap representing the game map.
     * @param x        The x-coordinate of the player.
     * @param y        The y-coordinate of the player.
     * @param size     The size of the player.
     * @return True if the player collides with any blocked tile, false otherwise.
     */
    private boolean isTilesBlocked(TiledMap tile_map, float x, float y, float size) {
        return isTileBlocked(tile_map, x, y) ||
                isTileBlocked(tile_map, x + size, y) ||
                isTileBlocked(tile_map, x, y + size) ||
                isTileBlocked(tile_map, x + size, y + size);
    }

    /**
     * Checks if a specific tile is blocked in the game map.
     *
     * @param tile_map The TiledMap representing the game map.
     * @param x        The x-coordinate of the tile.
     * @param y        The y-coordinate of the tile.
     * @return True if the tile is blocked, false otherwise.
     */
    private boolean isTileBlocked(TiledMap tile_map, float x, float y) {
        MapLayers layers = tile_map.getLayers();

        for (MapLayer layer : layers) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tile_layer = (TiledMapTileLayer) layer;

                int cellX = (int) (x / tile_layer.getTileWidth());
                int cellY = (int) (y / tile_layer.getTileHeight());


                TiledMapTileLayer.Cell cell = tile_layer.getCell(cellX, cellY);
                if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("Blocked")
                        && cell.getTile().getProperties().get("Blocked", Boolean.class)) {
                    return true; // Blocked tile found in any layer
                }
            }
        }
        return false; // No blocked tile found in any layer
    }

    /**
     * Moves the player by a specified amount.
     *
     * @param delta_x The change in x-coordinate.
     * @param delta_y The change in y-coordinate.
     */
    private void movePlayer(float delta_x, float delta_y) {
        this.player_x += delta_x;
        this.player_y += delta_y;
        this.player_x = MathUtils.clamp(this.player_x, 0, this.world_width - this.player_size);
        this.player_y = MathUtils.clamp(this.player_y, 0, this.world_height - this.player_size);
    }

    /**
     * Renders the player character on the game screen.
     *
     * @param renderer The OrthogonalTiledMapRenderer used for rendering.
     */
    public void render(OrthogonalTiledMapRenderer renderer) {
        Animation<TextureRegion> animation = PlayerAnimator.createAnimation(this.velocity);
        state_time += Gdx.graphics.getDeltaTime();
        TextureRegion current_frame = animation.getKeyFrame(state_time, true);

        renderer.getBatch().draw(current_frame, this.player_x, this.player_y, this.player_size, this.player_size);
    }

    /**
     * Handles user input to control the player movement.
     * Accelerates player's velocity in the appropriate direction when movement keys are pressed.
     * Friction is applied independently in x,y directions based on whether movement keys are pressed.
     * The player's velocity is clamped to prevent it from exceeding the maximum velocity.
     */
    public void handleInput() {
        boolean x_keys_pressed = false, y_keys_pressed = false;
        // Move player sprite based on key input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) { this.velocity.y += ACCELERATION; y_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.S)) { this.velocity.y -= ACCELERATION; y_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { this.velocity.x += ACCELERATION; x_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { this.velocity.x -= ACCELERATION; x_keys_pressed = true;}

        // Apply friction if no movement keys are pressed
        if(!x_keys_pressed) this.velocity.x *= FRICTION;
        if(!y_keys_pressed) this.velocity.y *= FRICTION;

        // Cap the player's velocity to ensure it stays within the max velocity
        this.velocity.x = MathUtils.clamp(this.velocity.x, -MAX_VELOCITY, MAX_VELOCITY);
        this.velocity.y = MathUtils.clamp(this.velocity.y, -MAX_VELOCITY, MAX_VELOCITY);
    }

    /**
     * Retrieves the current x-coordinate of the player.
     *
     * @return The x-coordinate of the player.
     */
    public float getPlayerX() {
        return this.player_x;
    }

    /**
     * Retrieves the current y-coordinate of the player.
     *
     * @return The y-coordinate of the player.
     */
    public float getPlayerY() {
        return this.player_y;
    }

    /**
     * Sets the location of the player to the specified coordinates.
     *
     * @param x The x-coordinate to set.
     * @param y The y-coordinate to set.
     */
    public void setPlayerLoc(float x, float y){
        this.player_x = x;
        this.player_y = y;
    }

    /**
     * Cleans up resources associated with the Avatar object.
     */
    public void dispose() {}
}
