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
public interface MoveableObject {
	/**
	 * moves the MoveableObject in a given direction
	 * @param direction the direction for the MoveableObject to move in
	 * @return true if the MoveableObject moved
	 */
	public boolean move(Direction direction);
}
