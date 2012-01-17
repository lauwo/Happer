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
	
	public Happer(Field field, Game game, int speed) {
		super(field, "images/happer/beneden.png");
		this.game = game;
		this.initialSpeed = speed;
		field.setGameObject(this);
		timer = new Timer(speed, happerMovement);
		timer.setInitialDelay(0);
		timer.start();
		slowDownTimer = new Timer(5000, slowDownHapper);
		currentDirection = Direction.DOWN;
	}
	
	public void moveToHuman() {
		move(Pathfinder.findShortestPath(getField()));
	}
	
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
	
	public void catchHuman() {
		timer.stop();
		game.lose();
	}
	
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
	
		
	ActionListener happerMovement = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			moveToHuman();
		}
	};

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public void slowDown() {
		timer.setDelay(timer.getDelay() * 2);
		slowDownTimer.restart();
		setCorrectImage();
	}
	
	ActionListener slowDownHapper = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			timer.setDelay(initialSpeed);
			((Timer)evt.getSource()).stop();
			setCorrectImage();
		}
	};
}
