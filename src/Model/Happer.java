/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;

/**
 *
 * @author Laurens
 */
public class Happer extends GameObject implements MoveableObject {

	public Happer(Field field) {
		super(field, "");
		field.setGameObject(this);
	}
	
	public void moveToHuman() {
		move(Pathfinder.findShortestPath(getField()));
	}
	
	public boolean move(Direction direction) {
		if (getField().getEmptyNeighbourFields().isEmpty()) {
			getField().getPlayField().getGame().win();
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (!newField.hasGameObject()) {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				getField().getPlayField().updateUI();
				return true;
			} else if (newField.getGameObject() instanceof Human) {
				catchHuman();
			}
		}
		return false;
	}
	
	public void catchHuman() {
		getField().getPlayField().getGame().lose();
	}
}
