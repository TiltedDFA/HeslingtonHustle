package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * The PlayerAnimator class manages animation of the player character when moving.
 * It selects the appropriate sprite sheet based on the player's current movement direction
 * and creates the animation accordingly.
 */
public class PlayerAnimator {
    // Constant rows and columns of the sprite sheet
    static Animation<TextureRegion> walk_animation;
    static Texture walk_down_sheet;
    static float state_time = 0f; // Tracks elapsed time of animation

    /**
     * Creates an animation based on the players movement velocity.
     *
     * @param velocity The velocity of the player.
     * @return The animation representing the player's movement.
     */
    public static Animation<TextureRegion> createAnimation(Vector2 velocity) {
        String sprite_sheet = getSpriteSheet(velocity);
//        System.out.println(sprite_sheet);
        int frame_rows;
        int frame_cols = 1;

        if (sprite_sheet.equals("player.png")) {
            frame_rows = 1;
        }
        else {
            frame_rows = 3;
        }

        // Load sprite sheet as a texture
        walk_down_sheet = new Texture(Gdx.files.internal(sprite_sheet));

        // Create 2d array of TextureRegions to match sprite sheet
        TextureRegion[][] tmp = TextureRegion.split(walk_down_sheet,
                walk_down_sheet.getWidth() / frame_cols,
                walk_down_sheet.getHeight() / frame_rows);

        // Convert to 1d array with animation frames in the correct order
        TextureRegion[] walkDownFrames = new TextureRegion[frame_cols * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                walkDownFrames[index] = tmp[i][j];
                index += 1;
            }
        }
        // Initialise animation with the frame interval (time spent on each animation frame)
        // and the array of frames.
        walk_animation = new Animation<>(0.1f, walkDownFrames);

        return walk_animation;
    }

    /**
     * Determines which sprite sheet to use based on the player's current movement direction.
     *
     * @param velocity The velocity of the player.
     * @return The filename of the sprite sheet to be used.
     */
    private static String getSpriteSheet(Vector2 velocity) {
        // Finds the direction the sprite is moving in so correct sprite sheet is used.

        if (Math.round(velocity.x) == 0 && Math.round(velocity.y) == 0) {
            // Check if player is standing still
            return "player.png";
        }
        else if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if (velocity.x < 0) {
                return "move_left_sprite_sheet.png";
            }
            else {
                return "move_right_sprite_sheet.png";
            }
        }
        else if (Math.abs(velocity.x) < Math.abs(velocity.y)) {
            if (velocity.y < 0) {
                return "move_down_sprite_sheet.png";
            }
            else {
                return "move_up_sprite_sheet.png";
            }
        }
        return "player.png";
    }
}
