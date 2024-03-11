package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
public class Avatar
{
    private final Texture player_sprite; // Player image
    private final float player_size;
    private float player_x, player_y;
    private final Vector2 velocity;

    private final float world_height;
    private final float world_width;
    private final static float ACCELERATION = 10f;
    private final static float FRICTION = 0.91f;

    private final static float MAX_VELOCITY = 400f;
    public Avatar(float plyr_x, float plyr_y, float world_height, float world_width)
    {
        this.player_x = plyr_x;
        this.player_y = plyr_y;
        this.velocity = new Vector2(0,0);
//        this.velocity_y = 0;
        this.player_size = 24f;
        this.player_sprite = new Texture("player.png");
        this.world_height = world_height;
        this.world_width = world_width;
    }
    public void update()
    {
        this.velocity.clamp(0, MAX_VELOCITY);
        this.player_x += this.velocity.x * Gdx.graphics.getDeltaTime();
        this.player_y += this.velocity.y * Gdx.graphics.getDeltaTime();

        this.player_x = MathUtils.clamp(this.player_x, 0, this.world_width - this.player_size);
        this.player_y = MathUtils.clamp(this.player_y, 0, this.world_height - this.player_size);
    }
    public void render(OrthogonalTiledMapRenderer renderer)
    {
        renderer.getBatch().draw(player_sprite, player_x, player_y, player_size, player_size);
    }
    public void handleInput()
    {
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
    public void dispose()
    {
        this.player_sprite.dispose();
    }
}
