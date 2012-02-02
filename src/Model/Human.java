/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import Components.HumanState;
import Event.SlowDownListener;
import Event.UpdateListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Human extends MoveableObject implements KeyListener {
	
	private HumanState status;
	private Timer immunityTimer;
	private Direction currentDirection;
	private boolean currentlyMoving;
	private HashMap<Direction, BufferedImage> standardImages;
	private HashMap<Direction, BufferedImage> immuneImages;
	private UpdateListener updateListener;
	private SlowDownListener slowDownListener;
	
	/**
	 * creates a new human
	 * @param field the field that the human should be located upon
	 * @param game the game that the human is involved in
	 */
	public Human(Field field, SlowDownListener slowDownListener, UpdateListener updateListener) {
		super(field);
		field.setGameObject(this);
		this.status = HumanState.NORMAL;
		this.currentlyMoving = false;
		setImmunityTimer();
		currentDirection = Direction.DOWN;
		standardImages = new HashMap<Direction, BufferedImage>();
		immuneImages = new HashMap<Direction, BufferedImage>();
		this.updateListener = updateListener;
		this.slowDownListener = slowDownListener;
		loadImages();
		setCorrectImage();
	}
	
	private void loadImages() {
		try {
			String imgLeft = "images/mens/links.png";
			String imgRight = "images/mens/rechts.png";
			String imgUp = "images/mens/boven.png";
			String imgDown = "images/mens/onder.png";
			String imgLeftImmune = "images/mens/linksshield.png";
			String imgRightImmune = "images/mens/rechtsshield.png";
			String imgUpImmune = "images/mens/bovenshield.png";
			String imgDownImmune = "images/mens/ondershield.png";
			BufferedImage img = ImageIO.read(new File(imgLeft));
			standardImages.put(Direction.LEFT, img);
			img = ImageIO.read(new File(imgRight));
			standardImages.put(Direction.RIGHT, img);
			img = ImageIO.read(new File(imgUp));
			standardImages.put(Direction.UP, img);
			img = ImageIO.read(new File(imgDown));
			standardImages.put(Direction.DOWN, img);
			img = ImageIO.read(new File(imgLeftImmune));
			immuneImages.put(Direction.LEFT, img);
			img = ImageIO.read(new File(imgRightImmune));
			immuneImages.put(Direction.RIGHT, img);
			img = ImageIO.read(new File(imgUpImmune));
			immuneImages.put(Direction.UP, img);
			img = ImageIO.read(new File(imgDownImmune));
			immuneImages.put(Direction.DOWN, img);
		} catch (IOException ex) {

		}
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
						updatePlayfield();
						return true;
					}
				} else if (newField.getGameObject() instanceof PowerUp) {
					if (newField.getGameObject() instanceof ImmunityShield)
						becomeImmune();
					else if (newField.getGameObject() instanceof SlowDown)
						slowDownHappers();
					newField.setGameObject(getField().getGameObject());
					getField().setGameObject(null);
					setField(newField);
					updatePlayfield();
				}			
			} else  {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				updatePlayfield();
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
	
	private void updatePlayfield() {
		updateListener.updatePlayfield();
	}
	
	private void slowDownHappers() {
		slowDownListener.slowDown();
	}
	
	/**
	 * sets the correct image for the human to use based on what direction the human is currently facing in and on the current state of the human
	 */
	private void setCorrectImage() {
		switch (currentDirection) {
			case LEFT:
			if (status == HumanState.IMMUNE)
				super.setImage(immuneImages.get(Direction.LEFT));
			else
				super.setImage(standardImages.get(Direction.LEFT));
			break;
			case RIGHT:
			if (status == HumanState.IMMUNE)
				super.setImage(immuneImages.get(Direction.RIGHT));
			else
				super.setImage(standardImages.get(Direction.RIGHT));
			break;
			case DOWN:
			if (status == HumanState.IMMUNE)
				super.setImage(immuneImages.get(Direction.DOWN));
			else
				super.setImage(standardImages.get(Direction.DOWN));
			break;
			case UP:
			if (status == HumanState.IMMUNE)
				super.setImage(immuneImages.get(Direction.UP));
			else
				super.setImage(standardImages.get(Direction.UP));						
			break;
		}
		updatePlayfield();	
	}
	
		/**
	 * this method is called whenever the actor first presses and then releases a key
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	/**
	 * this method gets called whenever the actor presses a key
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!currentlyMoving) {
			currentlyMoving = true;
			 switch(e.getKeyCode())	{
				case KeyEvent.VK_DOWN:
					move(Direction.DOWN);
					break;
				case KeyEvent.VK_UP:
					move(Direction.UP);
					break;
				case KeyEvent.VK_RIGHT:
					 move(Direction.RIGHT);
					break;
				case KeyEvent.VK_LEFT:
					 move(Direction.LEFT);
					break;
				default:
					
				break;
			}   
		}
	}

	/**
	 * this method is called whenever the actor releases a key
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		currentlyMoving = false;
	}
}