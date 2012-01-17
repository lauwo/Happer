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
		super(field, "images/huisjes/" + (int)((Math.random() * 13) + 1) + ".png");
		field.setGameObject(this);
	}
	
	private String getImageString() {
		
		return "";
	}
}
