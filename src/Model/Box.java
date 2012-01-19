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
	
	/**
	 * creates for a box
	 * @param field the field that the box should be located on
	 */
	public Box(Field field) {
		super(field, "images/pion.png");
	}
	
	/**
	 * moves the box in a given direction
	 * @param direction the direction for the box to move in
	 * @return true if the box moved
	 */
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
						return true;
					}
				} else if (newField.getGameObject() instanceof PowerUp) {
					newField.setGameObject(getField().getGameObject());
					getField().setGameObject(null);
					setField(newField);
					return true;
				}	
			} else  {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				return true;
			}
		}
		return false;
	}
}
