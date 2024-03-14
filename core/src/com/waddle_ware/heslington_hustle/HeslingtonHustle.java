package com.waddle_ware.heslington_hustle;

import com.badlogic.gdx.Game;
import com.waddle_ware.heslington_hustle.Screens.MenuScreen;

/**
 * The main class representing the game, extending LibGDX's Game class.
 * This class initialises the game and sets the initial screen to the PlayScreen.
 */
public class HeslingtonHustle extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
