package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x, y; // Sprite position

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		x = 0;
		y = 0;
	}

	@Override
	public void render () {
		handleInput(); // Call method to handle inputs
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, x, y); // Draw sprite in updated position
		batch.end();
	}

	private void handleInput () {
		float speed = 200f; // Sprite speed

		// Move sprite based on key input
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			y += Gdx.graphics.getDeltaTime() * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			y -= Gdx.graphics.getDeltaTime() * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			x -= Gdx.graphics.getDeltaTime() * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += Gdx.graphics.getDeltaTime() * speed;
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
