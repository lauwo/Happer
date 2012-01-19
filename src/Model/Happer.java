/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Laurens
 */
public class Happer extends GameObject implements MoveableObject {

	private Game game;
	private Timer timer;
	private int initialSpeed;
	private Timer slowDownTimer;
	private Direction currentDirection;
	
	/**
	 * creates a new happer
	 * @param field the field the happer should be located on
	 * @param game the game the happer is involved in
	 * @param speed the speed that the happer should move in
	 */
	public Happer(Field field, Game game, int speed) {
		super(field, "images/happer/beneden.png");
		this.game = game;
		this.initialSpeed = speed;
		field.setGameObject(this);
		setHapperMovement();
		setSlowDownTimer();		
		currentDirection = Direction.DOWN;
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
			game.win();
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.isWalkable()) {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				game.getPlayfield().updateUI();
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
		game.lose();
	}
	
	/**
	 * Sets the correct image for the happer based on the current direction the happer is facing towards and whether the happer is slowed down or not
	 */
	private void setCorrectImage() {
		switch (currentDirection) {
			case LEFT:
			if (slowDownTimer.isRunning())
				super.setImage("images/happer/linksslow.png");
			else
				super.setImage("images/happer/links.png");
			break;
			case RIGHT:
			if (slowDownTimer.isRunning())
				super.setImage("images/happer/rechtsslow.png");
			else
				super.setImage("images/happer/rechts.png");
			break;
			case DOWN:
			if (slowDownTimer.isRunning())
				super.setImage("images/happer/benedenslow.png");
			else
				super.setImage("images/happer/beneden.png");
			break;
			case UP:
			if (slowDownTimer.isRunning())
				super.setImage("images/happer/bovenslow.png");
			else
				super.setImage("images/happer/boven.png");
			break;
		}
		game.getPlayfield().updateUI();
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
}
