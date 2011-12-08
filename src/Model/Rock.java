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

	public Rock(Field field) {
		super(field, "images/rock.png");
		field.setGameObject(this);
	}	
}
