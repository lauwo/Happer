/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Difficulty;
import Components.GameState;
import View.Gameframe;
import View.Options;
import View.Playfield;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

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
	private Timer gameTimer;
	private int playfieldDimension;
	private int boxPercentage;
	private int rockPercentage;
	private Difficulty difficulty;
	
	public Game(Gameframe gameWindow) {
		boxPercentage = 20;
		rockPercentage = 10;
		difficulty = Difficulty.EASY;
		playfieldDimension = 20;
		gameTimer = new Timer(800, gameTimeActions);
		gameTimer.setInitialDelay(0);
		this.gameWindow = gameWindow;
		gameState = GameState.STOPPED;
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		initGameWindow();
	}
	
	private void initGameWindow() {
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setRequestFocusEnabled(true);
		gameWindow.pack();
	}
	
	private void initPlayfield(int fieldDimension, int boxPercentage, int rockPercentage, Difficulty difficulty) {
		Dimension dimension = new Dimension(fieldDimension * Field.width + 16, fieldDimension * Field.height + 58);
		playfield = new Playfield(fieldDimension, this);
		gameWindow.setPreferredSize(dimension);
		playfield.addGameObjects(boxPercentage, rockPercentage);
		human = new Human(playfield.getRandomEmptyField());
		happer = new Happer(playfield.getRandomEmptyField());
		start();
	}

	private void adjustHapperSpeed() {
		int happerSpeed = difficulty == Difficulty.HARD ? 250 : difficulty == Difficulty.MEDIUM ? 500 : 750;		
		gameTimer.setDelay(happerSpeed);
	}
	
	public Playfield getPlayfield() {
		return playfield;
	}
	
	public void pause() {
		gameState = gameState.PAUSED;
		playfield.setEnabled(false);
	    gameTimer.stop();
	}
	
	public void start() {
		gameState = gameState.STARTED;
		playfield.setEnabled(true);
		gameTimer.start();
		playfield.requestFocus();
	}
	
	public void reset() {
		gameWindow.jPlayfieldPanel.removeAll();
		initPlayfield(playfieldDimension, boxPercentage, rockPercentage, difficulty);
		initGameWindow();
		adjustHapperSpeed();
		playfield.requestFocus();
	}
	
	public void stop() {
		gameState = gameState.STOPPED;
		pause();
	}

	public GameState getGameState() {
		return gameState;
	}

	public Happer getHapper() {
		return happer;
	}

	public Human getHuman() {
		return human;
	}
	
	ActionListener gameTimeActions = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			happer.moveToHuman();
		}
	};
	
	public void showOptionsPanel() {
		pause();
		Options optionsPanel = new Options(this);
		playfield.setVisible(false);
		gameWindow.jPlayfieldPanel.add(optionsPanel);
	}
	
	public void showPlayfieldPanel() {
		gameWindow.jPlayfieldPanel.removeAll();
		gameWindow.jPlayfieldPanel.add(playfield);
		playfield.setVisible(true);
		start();
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
		gameWindow.jPlayfieldPanel.removeAll();
	}
	
	public void lose() {
		this.gameState = GameState.LOST;
	}
}
