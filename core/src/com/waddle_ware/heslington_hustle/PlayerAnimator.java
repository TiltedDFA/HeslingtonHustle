package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerAnimator {
    //constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 1, FRAME_ROWS = 2;
    static Animation<TextureRegion> walk_animation;
    static Texture walk_down_sheet;
    static float state_time = 0f; //tracks elapsed time of animation

    public static Animation<TextureRegion> createAnimation(Vector2 velocity) {
        String sprite_sheet = null;
        if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if (velocity.x < 0) {
                sprite_sheet = "move_left_spritesheet.png";
            }
            else {
                sprite_sheet = "move_right_spritesheet.png";
            }
        }
        else {
            if (velocity.y < 0) {
                sprite_sheet = "move_down_spritesheet.png";
            }
            else {
                sprite_sheet = "move_up_spritesheet.png";
            }
        }

        //load sprite sheet as a texture
        walk_down_sheet = new Texture(Gdx.files.internal(sprite_sheet));

        //create 2d array of TextureRegions to match sprite sheet
        TextureRegion[][] tmp = TextureRegion.split(walk_down_sheet,
                walk_down_sheet.getWidth() / FRAME_COLS,
                walk_down_sheet.getHeight() / FRAME_ROWS);

        //convert to 1d array with animation frames in the correct order
        TextureRegion[] walkDownFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkDownFrames[index] = tmp[i][j];
                index += 1;
            }
        }
        //initialize animation with the frame interval (time spent on each animation frame)
        //and the array of frames.
        walk_animation = new Animation<TextureRegion>(0.1f, walkDownFrames);

        return walk_animation;
    }
}
