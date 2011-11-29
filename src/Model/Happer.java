/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import View.Playfield;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author Laurens
 */
public class Happer extends GameObject implements MoveableObject {
	
	Playfield playField;

    int x;
    int y;
	
	public Happer(Field field) {
		super(field, "images/happer.png");
	}
	
	@Override
	public void move(Direction direction) {
		Field newField = field.getNeighbourField(direction);		
		newField.setGameObject(field.getGameObject());
		field.setGameObject(null);
	}
}
