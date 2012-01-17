/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Difficulty;
import Components.GameState;
import Components.HumanState;
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
public class Game {

	private Gameframe gameWindow;
	private Playfield playfield;
	private GameState gameState;
	private int playfieldDimension;
	private int boxPercentage;
	private int rockPercentage;
	private Difficulty difficulty;
	private Human human;
	private Happer happer;
	private Timer powerupTimer;
	
	public Game(Gameframe gameWindow) {
		boxPercentage = 20;
		rockPercentage = 10;
		difficulty = Difficulty.EASY;
		playfieldDimension = 20;
		this.gameWindow = gameWindow;
		gameState = GameState.STOPPED;
		initGameWindow();
	}
	
	private void initGameWindow() {
		StartPanel startPanel = new StartPanel(this);
		gameWindow.jPlayfieldPanel.add(startPanel);
		gameWindow.setResizable(false);
		gameWindow.setPreferredSize(startPanel.getPreferredSize());
		gameWindow.pack();
	}
	
	private void initPlayfield(int fieldDimension, int boxPercentage, int rockPercentage, Difficulty difficulty) {
		Dimension dimension = new Dimension(fieldDimension * Field.width + 16, fieldDimension * Field.height + 58);
		this.playfield = null;
		if (happer != null)
			happer.getTimer().stop();
		if (powerupTimer != null)
			powerupTimer.stop();
		happer = null;
		human = null;
		playfield = new Playfield(fieldDimension, this);
		gameWindow.setSize(dimension);
		playfield.addGameObjects(boxPercentage, rockPercentage);
		int happerSpeed = difficulty == Difficulty.HARD ? 250 : difficulty == Difficulty.MEDIUM ? 500 : 750;
		happer = new Happer(playfield.getRandomEmptyField(), this, happerSpeed);
		human = new Human(playfield.getRandomEmptyField(), this);
		activatePowerUps();
		resume();
	}
	
	public Playfield getPlayfield() {
		return playfield;
	}
	
	public void pause() {
		gameState = gameState.PAUSED;
		playfield.setEnabled(false);
	    happer.getTimer().stop();
		showStatusPanel();
	}
	
	public void start() {
		gameState = gameState.STARTED;
		gameWindow.jPlayfieldPanel.removeAll();
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setRequestFocusEnabled(true);
	}
	
	public void resume() {
		gameState = gameState.STARTED;
		restorePlayfield();
		playfield.setEnabled(true);
		happer.getTimer().start();
		playfield.requestFocus();
	}
	
	public void reset() {
		gameWindow.jPlayfieldPanel.removeAll();
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		start();
		playfield.requestFocus();
	}
	
	public void stop() {
		pause();
		gameState = gameState.STOPPED;
		showStatusPanel();
	}

	public GameState getState() {
		return gameState;
	}
	
	public void showOptionsPanel() {
		if (getState() == GameState.STARTED)
			pause();
			
		Options optionsPanel = new Options(this);
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(optionsPanel);
		gameWindow.setSize(optionsPanel.getPreferredSize());
	}

	public int getBoxPercentage() {
		return boxPercentage;
	}

	public void setBoxPercentage(int boxPercentage) {
		this.boxPercentage = boxPercentage;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getRockPercentage() {
		return rockPercentage;
	}

	public void setRockPercentage(int rockPercentage) {
		this.rockPercentage = rockPercentage;
	}

	public int getPlayfieldDimension() {
		return playfieldDimension;
	}

	public void setPlayfieldDimension(int fieldDimension) {
		this.playfieldDimension = fieldDimension;
	}
	
	public void win() {
		stop();
		this.gameState = GameState.WON;
		showStatusPanel();
	}
	
	public void lose() {
		stop();
		this.gameState = GameState.LOST;
		showStatusPanel();
	}
	
	public Human getHuman() {
		return human;
	}
	
	private void showStatusPanel() {
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(new StatusPanel(this));
		Dimension statusDimension = new Dimension(400, 400);
		gameWindow.setSize(statusDimension);
	}
	
	public void restorePlayfield() {
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setVisible(true);
		Dimension dimension = new Dimension(playfieldDimension * Field.width + 16, playfieldDimension * Field.height + 58);
		gameWindow.setSize(dimension);
	}
	
	public void slowDownHapper() {
		happer.slowDown();
	}
	
	public void activatePowerUps() {
		powerupTimer = new Timer(10000, powerUpSpawner);
		powerupTimer.start();
	}
	
	public void spawnPowerUp() {
		int random = (int)(Math.random() * 2) + 1;
		PowerUp powerUp;
		if (random == 1)
			powerUp = new ImmunityShield(playfield.getRandomEmptyField());
		else if (random == 2)
			powerUp = new SlowDown(playfield.getRandomEmptyField(), this);
	}
	
	ActionListener powerUpSpawner = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			spawnPowerUp();
		}
	};
}
