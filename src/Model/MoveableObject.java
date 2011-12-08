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
public abstract class MoveableObject extends GameObject {
	public MoveableObject(Field field) {
		super(field, "images/happer.png");
		field.setGameObject(this);
	}
	
	public boolean move(Direction direction) {
		Field newField = field.getNeighbourField(direction);				
		if (newField != null) {
			if (newField.getGameObject() != null) {
				if (newField.getGameObject() instanceof Box) {
					Box box = (Box)newField.getGameObject();
					if (box.move(direction)) {
						newField.setGameObject(field.getGameObject());
						field.setGameObject(null);
						field = newField;
						field.getPlayField().updateUI();
						return true;
					}
				}				
			} else  {
				newField.setGameObject(field.getGameObject());
				field.setGameObject(null);
				field = newField;
				field.getPlayField().updateUI();
				return true;
			}
		}
		return false;
	}
}
