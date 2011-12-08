/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.GameState;
import Components.Logger;
import View.Gameframe;
import View.Playfield;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Laurens
 */
public class Game {

	private Gameframe gameWindow;
	private Playfield playfield;
	private GameState gameState;
	
	public Game(Gameframe gameWindow) {
		this.gameWindow = gameWindow;
		//this.playfield = playfield;
		gameState = GameState.STOPPED;
		initPlayfield();
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
	
	private void initPlayfield()
	{
       playfield = new Playfield(20);
       Logger.log("Playfield initialised.");
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
