/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.GameState;
import Components.Logger;
import View.Gameframe;
import View.Playfield;

/**
 *
 * @author Laurens
 */
public class Game {

	private Gameframe gameWindow;
	private Playfield playfield;
	private GameState gameState;
	private Human human;
	private Happer happer;
	
	public Game(Gameframe gameWindow) {
		this.gameWindow = gameWindow;
		//this.playfield = playfield;
		gameState = GameState.STOPPED;
		initPlayfield();
		//human = new Human(playfield.getRandomField());
		initGameWindow();
		Logger.log("Game initialised.");
	}
	
	private void initGameWindow() {
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setRequestFocusEnabled(true);
		gameWindow.setBounds(playfield.getBounds());
		gameWindow.pack();
		Logger.log("GameWindow initialised.");
	}
	
	private void initPlayfield() {
       playfield = new Playfield(20);
       Logger.log("Playfield initialised.");
	   //playfield.addGameObject(new Human());
	   //playfield.addGameObject(human);
	}

	public Playfield getPlayfield() {
		return playfield;
	}
	
	public void pause() {
		playfield.setEnabled(false);
	}
	
	public void start() {
		playfield.setEnabled(true);
	}
	
}
