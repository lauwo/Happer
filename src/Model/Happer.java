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
	
	public Happer(Field field, Game game, int speed) {
		super(field, "images/happer/autobeneden.png");
		this.game = game;
		this.initialSpeed = speed;
		field.setGameObject(this);
		timer = new Timer(speed, happerMovement);
		timer.setInitialDelay(0);
		timer.start();
		slowDownTimer = new Timer(5000, slowDownHapper);
	}
	
	public void moveToHuman() {
		move(Pathfinder.findShortestPath(getField()));
	}
	
	public boolean move(Direction direction) {
		if (direction != null) {		
			switch (direction) {
				case LEFT:
					super.setImage("images/happer/autolinks.png");
				break;
				case RIGHT:
					super.setImage("images/happer/autorechts.png");
				break;
				case DOWN:
					super.setImage("images/happer/autobeneden.png");
				break;
				case UP:
					super.setImage("images/happer/autoboven.png");
				break;
			}
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
		timer.setDelay(timer.getDelay() + 250);
		slowDownTimer.restart();
	}
	
	ActionListener slowDownHapper = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			timer.setDelay(initialSpeed);
			((Timer)evt.getSource()).stop();
		}
	};
}
