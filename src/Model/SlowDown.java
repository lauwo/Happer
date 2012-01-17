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
	
	private Game game;
	
	public SlowDown(Field field, Game game) {
		super(field, "images/lekkeband.png");
		field.setGameObject(this);
		this.game = game;
	}
	
	public void onPickUp() {
		
	}
}
