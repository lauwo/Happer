/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Laurens
 */
public class ImmunityShield extends GameObject implements PowerUp {
	
	public ImmunityShield(Field field) {
		super(field, "images/shield.png");
		field.setGameObject(this);
	}
	
	public void onPickUp() {
		
	}
	
}
