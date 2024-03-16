package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.maps.tiled.TiledMap;

import static com.waddle_ware.heslington_hustle.PlayerAnimator.state_time;

public class Avatar {
    private final Texture player_sprite; // Player image
    private final float player_size;
    private float player_x, player_y;
    private final Vector2 velocity;

    private final float world_height;
    private final float world_width;
    private final static float ACCELERATION = 10f;
    private final static float FRICTION = 0.8f;

    private final static float MAX_VELOCITY = 200f;
    public Avatar(float plyr_x, float plyr_y, float world_height, float world_width) {
        this.player_x = plyr_x;
        this.player_y = plyr_y;
        this.velocity = new Vector2(0,0);
        this.player_size = 24f;
        this.player_sprite = new Texture("player.png");
        this.world_height = world_height;
        this.world_width = world_width;
    }
    public void update(TiledMap tile_map)
    {
        float oldX = this.player_x;
        float oldY = this.player_y;

        this.velocity.clamp(0, MAX_VELOCITY);
        movePlayer(this.velocity.x * Gdx.graphics.getDeltaTime(), this.velocity.y * Gdx.graphics.getDeltaTime());

        if(isTilesBlocked(tile_map, this.player_x, this.player_y, this.player_size))
        {
            onCollision(tile_map, oldX, oldY);
        }
    }

    public void onCollision(TiledMap tile_map, float oldX, float oldY) {
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


    private boolean isTilesBlocked(TiledMap tile_map, float x, float y, float size) {
        return isTileBlocked(tile_map, x, y) ||
                isTileBlocked(tile_map, x + size, y) ||
                isTileBlocked(tile_map, x, y + size) ||
                isTileBlocked(tile_map, x + size, y + size);
    }

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
    private void movePlayer(float delta_x, float delta_y) {
        this.player_x += delta_x;
        this.player_y += delta_y;
        this.player_x = MathUtils.clamp(this.player_x, 0, this.world_width - this.player_size);
        this.player_y = MathUtils.clamp(this.player_y, 0, this.world_height - this.player_size);
    }
    public void render(OrthogonalTiledMapRenderer renderer) {
        Animation<TextureRegion> animation = PlayerAnimator.createAnimation(velocity);
        state_time += Gdx.graphics.getDeltaTime();
        TextureRegion current_frame = animation.getKeyFrame(state_time, true);

        renderer.getBatch().draw(current_frame, player_x, player_y, player_size, player_size);
    }
    public void handleInput() {
        boolean x_keys_pressed = false, y_keys_pressed = false;
        // Move player sprite based on key input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) { velocity.y += ACCELERATION; y_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.S)) { velocity.y -= ACCELERATION; y_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { velocity.x -= ACCELERATION; x_keys_pressed = true;}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { velocity.x += ACCELERATION; x_keys_pressed = true;}

        if(!x_keys_pressed) velocity.x *= FRICTION;
        if(!y_keys_pressed) velocity.y *= FRICTION;

        velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY);
        velocity.y = MathUtils.clamp(velocity.y, -MAX_VELOCITY, MAX_VELOCITY);
    }

    public float getPlayerX() {
        return player_x;
    }

    public float getPlayerY() {
        return player_y;
    }

    public void dispose() {
        this.player_sprite.dispose();
    }
}
