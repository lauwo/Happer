/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author Laurens
 */
public class Human extends GameObject implements MoveableObject, KeyListener {

	
	public Human(Field field) {
		super(field, "images/happer.png");
		field.setGameObject(this);
	}
	
	@Override
	public void move(Direction direction) {
		Field newField = field.getNeighbourField(direction);
		if (field.getNeighbourField(direction).getGameObject() instanceof Box) {
		}
		newField.setGameObject(field.getGameObject());
		field.setGameObject(null);
		field.getPlayField().updateUI();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                 move(Direction.DOWN);
                break;
            case KeyEvent.VK_UP:
				move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                 move(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                 move(Direction.LEFT);
                break;
        default:
            break;
        }   
    }

	@Override
    public void keyReleased(KeyEvent e) {

    }
	
	@Override
	public void keyTyped(KeyEvent e) {

    }
}
