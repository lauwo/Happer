/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Difficulty;
import Components.GameState;
import Event.GameStateListener;
import View.Gameframe;
import View.Options;
import View.Playfield;
import View.StartPanel;
import View.StatusPanel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Laurens
 */
public class Game implements GameStateListener {

	private Gameframe gameWindow;
	private Playfield playfield;
	private GameState gameState;
	private int playfieldDimension;
	private int boxPercentage;
	private int rockPercentage;
	private Difficulty difficulty;
	private Timer powerupTimer;
	private GameStateListener gameStateListener;
	
	/**
	 * create a new game
	 * @param gameWindow the GameWindow the game should utilize to show itself
	 */
	public Game(Gameframe gameWindow) {
		boxPercentage = 15;
		rockPercentage = 10;
		difficulty = Difficulty.EASY;
		playfieldDimension = 20;
		this.gameWindow = gameWindow;
		gameState = GameState.STOPPED;
		initGameWindow();
	}
	
	
	/**
	 * initializes the game window
	 */
	private void initGameWindow() {
		gameWindow.setResizable(false);
		showStartPanel();
	}
	
	/**
	 * initializes the playfield
	 * @param fieldDimension the dimension the playfield
	 * @param boxPercentage the percentage of boxes on the field
	 * @param rockPercentage the percentage of rocks on the field
	 * @param difficulty the difficulty of the game
	 */
	private void initPlayfield(int fieldDimension, int boxPercentage, int rockPercentage, Difficulty difficulty) {
		Dimension dimension = new Dimension(fieldDimension * Field.width + 6, fieldDimension * Field.height + 51);
		this.playfield = null;
		if (powerupTimer != null)
			powerupTimer.stop();
		setPlayfield(new Playfield(fieldDimension, true));
		gameWindow.setSize(dimension);
		playfield.addGameObjects(boxPercentage, rockPercentage);
		int happerSpeed = difficulty == Difficulty.HARD ? 250 : difficulty == Difficulty.MEDIUM ? 500 : 750;
		Happer happer = playfield.addHapper(happerSpeed, this);
		gameStateListener = happer;
		playfield.addHuman(happer);
		activatePowerUps();
		resume();
	}
	
	/**
	 * retrieve the current playfield
	 * @return the current playfield
	 */
	public Playfield getPlayfield() {
		return playfield;
	}
	
	/**
	 * sets the playfield to be used in the game
	 * @param playfield the new playfield to be used 
	 */
	public void setPlayfield(Playfield playfield) {
		this.playfield = playfield;
	}
	
	/**
	 * pauses the game
	 */
	public void pause() {
		gameState = GameState.PAUSED;
		playfield.setEnabled(false);
		powerupTimer.stop();
		showStatusPanel();
		fireGameStateEvent(gameState);
	}
	
	/**
	 * starts the game
	 */
	public void start() {
		gameState = GameState.RUNNING;
		gameWindow.jPlayfieldPanel.removeAll();
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setRequestFocusEnabled(true);
		playfield.requestFocus();
		fireGameStateEvent(gameState);
	}
	
	/**
	 * resumes the game
	 */
	public void resume() {
		if (gameState == GameState.PAUSED) {
			restorePlayfield();
			playfield.setEnabled(true);
			gameState = GameState.RUNNING;
			powerupTimer.start();
			playfield.requestFocus();
		}
		fireGameStateEvent(gameState);
	}
	
	/**
	 * resets the game
	 */
	public void reset() {
		fireGameStateEvent(GameState.STOPPED);
		gameWindow.jPlayfieldPanel.removeAll();
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		start();
		playfield.requestFocus();
	}
	
	/**
	 * stops the game
	 */
	public void stop() {
		if (gameState == GameState.RUNNING)
			pause();
		gameState = gameState.STOPPED;
		fireGameStateEvent(gameState);
		showStatusPanel();
	}

	/**
	 * retrieve the current state of the game
	 * @return the current state of the game
	 */
	public GameState getState() {
		return gameState;
	}
	
	/**
	 * hide the playfield and show the option panel
	 */
	public void showOptionsPanel() {
		if (getState() == GameState.RUNNING)
			pause();
			
		Options optionsPanel = new Options(this);
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(optionsPanel);
		gameWindow.setSize(optionsPanel.getPreferredSize());
	}

	/**
	 * retrieve the current boxpercentage setting
	 * @return the percentage of boxes on the playfield
	 */
	public int getBoxPercentage() {
		return boxPercentage;
	}

	/**
	 * set the box percentage
	 * @param boxPercentage the new percentage of boxes on the playfield
	 */
	public void setBoxPercentage(int boxPercentage) {
		this.boxPercentage = boxPercentage;
	}

	/**
	 * retrieve the current difficulty of the game
	 * @return the current difficulty of the game
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * set the difficulty for the game
	 * @param difficulty the new level of difficulty
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * retrieve the current rockpercentage setting
	 * @return the percentage of rocks on the playfield
	 */
	public int getRockPercentage() {
		return rockPercentage;
	}

	/**
	 * sets the percentage of rocks on the playfield
	 * @param rockPercentage the new percentage of rocks on the playfield
	 */
	public void setRockPercentage(int rockPercentage) {
		this.rockPercentage = rockPercentage;
	}

	/**
	 * retrieve the current playfield dimension
	 * @return the current playfieldDimension
	 */
	public int getPlayfieldDimension() {
		return playfieldDimension;
	}

	/**
	 * set the playfield dimension
	 * @param fieldDimension the new playfield dimension
	 */
	public void setPlayfieldDimension(int fieldDimension) {
		this.playfieldDimension = fieldDimension;
	}
	
	/**
	 * called when the player wins the game, stops the game and shows new panel
	 */
	public void win() {
		stop();
		this.gameState = GameState.WON;
		showStatusPanel();
	}
	
	/**
	 * called when the player loses the game, stops the game and shows new panel
	 */
	public void lose() {
		stop();
		this.gameState = GameState.LOST;
		showStatusPanel();
	}
	
	/**
	 * shows a new panel that will check the state of the game and show information accordingly
	 */
	private void showStatusPanel() {
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(new StatusPanel(this));
		Dimension statusDimension = new Dimension(400, 400);
		gameWindow.setSize(statusDimension);
		gameWindow.setPreferredSize(statusDimension);
		gameWindow.pack();
	}
	
	/**
	 * shows the games start panel
	 */
	public void showStartPanel() {
		gameWindow.jPlayfieldPanel.removeAll();
		StartPanel startPanel = new StartPanel(this);
		gameWindow.jPlayfieldPanel.add(startPanel);
		gameWindow.setPreferredSize(startPanel.getPreferredSize());
		gameWindow.pack();
	}
	
	/**
	 * restores the playfield panel if a different panel was being shown (for example when the game was paused)
	 */
	public void restorePlayfield() {
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setVisible(true);
		Dimension dimension = new Dimension(playfieldDimension * Field.width + 8, playfieldDimension * Field.height + 50);
		gameWindow.setSize(dimension);
	}
	/**
	 * activates powerups for the current game
	 */
	public void activatePowerUps() {
		ActionListener powerUpSpawner = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				spawnPowerUp();
			}
		};
		powerupTimer = new Timer(1000, powerUpSpawner);
		powerupTimer.start();
	}
	
	/**
	 * spawns a random powerup on the playfield
	 */
	public void spawnPowerUp() {
		int random = (int)(Math.random() * 2) + 1;
		PowerUp powerUp;
		if (random == 1)
			powerUp = new ImmunityShield(playfield.getRandomEmptyField());
		else if (random == 2)
			powerUp = new SlowDown(playfield.getRandomEmptyField());
	}

	@Override
	public void gameStateChanged(GameState gameState) {
		this.gameState = gameState;
		switch (gameState) {
			case WON:
				win();
				break;
			case LOST:
				lose();
				break;				
		}
	}
	
	private void fireGameStateEvent(GameState newState) {
		gameStateListener.gameStateChanged(newState);
	}
}
