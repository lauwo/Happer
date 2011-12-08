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
public class Human extends MoveableObject {
	
	public Human(Field field) {
		super(field);
		field.setGameObject(this);
	}
}