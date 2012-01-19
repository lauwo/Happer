/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import Components.HumanState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
/**
 *
 * @author Laurens
 */
public class Human extends GameObject implements MoveableObject {
	
	private Game game;
	private HumanState status;
	private Timer immunityTimer;
	private Direction currentDirection;
	
	/**
	 * creates a new human
	 * @param field the field that the human should be located upon
	 * @param game the game that the human is involved in
	 */
	public Human(Field field, Game game) {
		super(field, "images/mens/onder.png");
		this.game = game;
		field.setGameObject(this);
		this.status = HumanState.NORMAL;
		setImmunityTimer();
		currentDirection = Direction.DOWN;
	}
	
	/**
	 * moves the human in a given direction
	 * @param direction the direction for the human to move in
	 * @return true if the human moved
	 */
	public boolean move(Direction direction) {
		if (direction != null) {
			currentDirection = direction;
			setCorrectImage();
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.hasGameObject()) {
				if (newField.getGameObject() instanceof Box) {
					Box box = (Box)newField.getGameObject();
					if (box.move(direction)) {
						newField.setGameObject(getField().getGameObject());
						getField().setGameObject(null);
						setField(newField);
						game.getPlayfield().updateUI();
						return true;
					}
				} else if (newField.getGameObject() instanceof PowerUp) {
					if (newField.getGameObject() instanceof ImmunityShield)
						becomeImmune();
					else if (newField.getGameObject() instanceof SlowDown)
						game.slowDownHapper();
					newField.setGameObject(getField().getGameObject());
					getField().setGameObject(null);
					setField(newField);
					game.getPlayfield().updateUI();
				}			
			} else  {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				game.getPlayfield().updateUI();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * makes the human immune to the happer for 5 seconds
	 */
	public void becomeImmune() {
		this.status = HumanState.IMMUNE;
		setCorrectImage();
		immunityTimer.restart();
	}
	
	/**
	 * removes the humans immunity status
	 */
	public void removeImmunity() {
		this.status = HumanState.NORMAL;
		setCorrectImage();
	}
	
	private void setImmunityTimer() {
		ActionListener removeImmunity = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				removeImmunity();
				((Timer)evt.getSource()).stop();
			}
		};
		immunityTimer = new Timer(5100, removeImmunity);
	}
	
	/**
	 * checks to see whether the human is immune or not
	 * @return true if the human is currently immune to the happer
	 */
	public boolean isImmune() {
		return this.status == HumanState.IMMUNE;
	}
	
	/**
	 * sets the correct image for the human to use based on what direction the human is currently facing in and on the current state of the human
	 */
	public void setCorrectImage() {
		switch (currentDirection) {
			case LEFT:
			if (status == HumanState.IMMUNE)
				super.setImage("images/mens/linksshield.png");
			else
				super.setImage("images/mens/links.png");
			break;
			case RIGHT:
			if (status == HumanState.IMMUNE)
				super.setImage("images/mens/rechtsshield.png");
			else
				super.setImage("images/mens/rechts.png");
			break;
			case DOWN:
			if (status == HumanState.IMMUNE)
				super.setImage("images/mens/ondershield.png");
			else
				super.setImage("images/mens/onder.png");
			break;
			case UP:
			if (status == HumanState.IMMUNE)
				super.setImage("images/mens/bovenshield.png");
			else
				super.setImage("images/mens/boven.png");						
			break;
		}
		game.getPlayfield().updateUI();		
	}
}