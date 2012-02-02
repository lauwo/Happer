/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import Components.GameState;
import Event.GameStateListener;
import Event.SlowDownListener;
import Event.UpdateListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Laurens
 */
public class Happer extends MoveableObject implements SlowDownListener, GameStateListener {

	private Timer timer;
	private int initialSpeed;
	private Timer slowDownTimer;
	private Direction currentDirection;
	private HashMap<Direction, BufferedImage> standardImages;
	private HashMap<Direction, BufferedImage> slowImages;
	private GameStateListener gameStateListener;
	private UpdateListener updateListener;
	
	/**
	 * creates a new happer
	 * @param field the field the happer should be located on
	 * @param game the game the happer is involved in
	 * @param speed the speed that the happer should move in
	 */
	public Happer(Field field, int speed, UpdateListener updateListener, GameStateListener gameStateListener) {
		super(field);
		this.initialSpeed = speed;
		field.setGameObject(this);
		setHapperMovement();
		setSlowDownTimer();
		currentDirection = Direction.DOWN;
		standardImages = new HashMap<Direction, BufferedImage>();
		slowImages = new HashMap<Direction, BufferedImage>();
		this.gameStateListener = gameStateListener;
		this.updateListener = updateListener;
		loadImages();
		setCorrectImage();
	}
	
	private void loadImages() {
		try {
			String imgLeft = "images/happer/links.png";
			String imgRight = "images/happer/rechts.png";
			String imgUp = "images/happer/boven.png";
			String imgDown = "images/happer/onder.png";
			String imgLeftSlow = "images/happer/linksslow.png";
			String imgRightSlow = "images/happer/rechtsslow.png";
			String imgUpSlow = "images/happer/bovenslow.png";
			String imgDownSlow = "images/happer/onderslow.png";
			BufferedImage img = ImageIO.read(new File(imgLeft));
			standardImages.put(Direction.LEFT, img);
			img = ImageIO.read(new File(imgRight));
			standardImages.put(Direction.RIGHT, img);
			img = ImageIO.read(new File(imgUp));
			standardImages.put(Direction.UP, img);
			img = ImageIO.read(new File(imgDown));
			standardImages.put(Direction.DOWN, img);
			img = ImageIO.read(new File(imgLeftSlow));
			slowImages.put(Direction.LEFT, img);
			img = ImageIO.read(new File(imgRightSlow));
			slowImages.put(Direction.RIGHT, img);
			img = ImageIO.read(new File(imgUpSlow));
			slowImages.put(Direction.UP, img);
			img = ImageIO.read(new File(imgDownSlow));
			slowImages.put(Direction.DOWN, img);
		} catch (IOException ex) {

		}
	}
	
	/**
	 * moves the happer towards the human
	 */
	public void moveToHuman() {
		move(Pathfinder.findShortestPath(getField()));
	}
	
	/**
	 * moves the happer in a given direction
	 * @param direction the direction for the happer to move in
	 * @return true if the happer moved
	 */
	public boolean move(Direction direction) {
		if (direction != null) {
			currentDirection = direction;
			setCorrectImage();
		}
		if (getField().getEmptyNeighbourFields().isEmpty()) {
			timer.stop();
			fireGameStateEvent(GameState.WON);
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (!newField.hasGameObject() || newField.getGameObject() instanceof PowerUp) {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				updatePlayfield();
				return true;
			} else if (newField.getGameObject() instanceof Human) {
				Human human = (Human)newField.getGameObject();
				if (!human.isImmune())
					catchHuman();
			}
		}
		return false;
	}
	
	/**
	 * this method gets called when the happer catches the human
	 */
	private void catchHuman() {
		timer.stop();
		fireGameStateEvent(GameState.LOST);
	}
	
	private void fireGameStateEvent(GameState gameState) {
		gameStateListener.gameStateChanged(gameState);
	}
	
	private void updatePlayfield() {
		updateListener.updatePlayfield();
	}
	
	/**
	 * Sets the correct image for the happer based on the current direction the happer is facing towards and whether the happer is slowed down or not
	 */
	private void setCorrectImage() {
		switch (currentDirection) {
			case LEFT:
			if (slowDownTimer.isRunning())
				super.setImage(slowImages.get(Direction.LEFT));
			else
				super.setImage(standardImages.get(Direction.LEFT));
			break;
			case RIGHT:
			if (slowDownTimer.isRunning())
				super.setImage(slowImages.get(Direction.RIGHT));
			else
				super.setImage(standardImages.get(Direction.RIGHT));
			break;
			case DOWN:
			if (slowDownTimer.isRunning())
				super.setImage(slowImages.get(Direction.DOWN));
			else
				super.setImage(standardImages.get(Direction.DOWN));
			break;
			case UP:
			if (slowDownTimer.isRunning())
				super.setImage(slowImages.get(Direction.UP));
			else
				super.setImage(standardImages.get(Direction.UP));
			break;
		}
		updatePlayfield();
	}
	
	private void setHapperMovement() {
		ActionListener happerMovement = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				moveToHuman();
			}
		};
		timer = new Timer(initialSpeed, happerMovement);
		timer.setInitialDelay(0);
		timer.start();
	}

	/**
	 * retrieve the happers timer
	 * @return the timer that makes the happer move
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * makes the happer move twice as slow as his set starting speed for 5 seconds
	 */
	public void slowDown() {
		timer.setDelay(initialSpeed * 2);
		slowDownTimer.restart();
		setCorrectImage();
	}
	
	private void setSlowDownTimer() {
		ActionListener slowDownHapper = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timer.setDelay(initialSpeed);
				((Timer)evt.getSource()).stop();
				setCorrectImage();
			}
		};
		slowDownTimer = new Timer(5000, slowDownHapper);
	}

	@Override
	public void gameStateChanged(GameState state) {
		switch (state) {
			case STOPPED:
				timer.stop();
				break;
			case PAUSED:
				timer.stop();
				break;
			case RUNNING:
				timer.start();
				break;
		}
	} 
}
