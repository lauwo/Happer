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
public class Box extends GameObject implements MoveableObject {
	
	public Box(Field field) {
		super(field, "");
	}
	
	public boolean move(Direction direction) {
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.hasGameObject()) {
				if (newField.getGameObject() instanceof Box) {
					Box box = (Box)newField.getGameObject();
					if (box.move(direction)) {
						newField.setGameObject(getField().getGameObject());
						getField().setGameObject(null);
						setField(newField);
						getField().getPlayField().updateUI();
						return true;
					}
				}				
			} else  {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				getField().getPlayField().updateUI();
				return true;
			}
		}
		return false;
	}
}
