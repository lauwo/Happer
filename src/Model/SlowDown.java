/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Laurens
 */
public class SlowDown extends GameObject implements PowerUp {
	
	/**
	 * creates a new slowdown powerup
	 * @param field the field the slowdown should be located upon
	 */
	public SlowDown(Field field) {
		super(field, "images/lekkeband.png");
		field.setGameObject(this);
	}
}
