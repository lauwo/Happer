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
public class Human extends GameObject implements MoveableObject {
	
	private Game game;
	
	public Human(Field field, Game game) {
		super(field, "");
		this.game = game;
		field.setGameObject(this);
	}
	
	public boolean move(Direction direction) {
		Field newField = getField().getNeighbourField(direction);				
		if (newField != null) {
			if (newField.hasGameObject()) {
				if (newField.getGameObject() instanceof Box) {
					Box box = (Box)newField.getGameObject();
					if (box.move(direction)) {
						newField.setGameObject(getField().getGameObject());
						getField().setGameObject(null);
						setField(newField);
						game.getPlayfield().updateUI();
						return true;
					}
				}				
			} else  {
				newField.setGameObject(getField().getGameObject());
				getField().setGameObject(null);
				setField(newField);
				game.getPlayfield().updateUI();
				return true;
			}
		}
		return false;
	}
	
}