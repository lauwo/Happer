/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Laurens
 */
public class Rock extends GameObject {
	
	/**
	 * creates a new rock
	 * @param field the field the rock should be located upon
	 */
	public Rock(Field field) {		
		super(field, "images/huisjes/" + (int)((Math.random() * 13) + 1) + ".png");
		field.setGameObject(this);
	}
	
}
