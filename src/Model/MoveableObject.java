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
		super(field);
	}
	/**
	 * moves the MoveableObject in a given direction
	 * @param direction the direction for the MoveableObject to move in
	 * @return true if the MoveableObject moved
	 */
	public abstract boolean move(Direction direction);
}
