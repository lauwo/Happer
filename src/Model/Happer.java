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
	
	public Happer(Field field, Game game, int speed) {
		super(field, "");
		this.game = game;
		field.setGameObject(this);
		timer = new Timer(speed, gameTimeActions);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	public void moveToHuman() {
		move(Pathfinder.findShortestPath(getField()));
	}
	
	public boolean move(Direction direction) {
		if (getField().getEmptyNeighbourFields().isEmpty()) {
			game.win();
			timer.stop();
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (!newField.hasGameObject()) {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				game.getPlayfield().updateUI();
				return true;
			} else if (newField.getGameObject() instanceof Human) {
				catchHuman();
				timer.stop();
			}
		}
		return false;
	}
	
	public void catchHuman() {
		game.lose();
	}
	
		
	ActionListener gameTimeActions = new ActionListener() {
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
}
