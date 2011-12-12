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
public class Human extends GameObject implements MoveableObject {
	
	public Human(Field field) {
		super(field, "");
		field.setGameObject(this);
	}
	
	public boolean move(Direction direction) {
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.getGameObject() != null) {
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