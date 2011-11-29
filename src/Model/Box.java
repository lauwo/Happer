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
	
	@Override
	public void move(Direction direction) {
		Field newField = field.getNeighbourField(direction);		
		newField.setGameObject(field.getGameObject());
		field.setGameObject(null);
	}
}
