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
	
	public boolean move(Direction direction) {
		if (getField().getEmptyNeighbourFields().isEmpty()) {
			getField().getPlayField().getGame().stop();
		}
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.getGameObject() == null) {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				getField().getPlayField().updateUI();
				return true;
			} else if (newField.getGameObject() instanceof Human) {
				caughtHuman();
			}
		}
		return false;
	}
	
	public void caughtHuman() {
		getField().getPlayField().getGame().stop();
	}
}
