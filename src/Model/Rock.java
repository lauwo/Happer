/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
		super(field);
		field.setGameObject(this);
		try {
			BufferedImage image = ImageIO.read(new File("images/huisjes/" + (int)((Math.random() * 13) + 1) + ".png"));
			super.setImage(image);
		} catch (IOException ex) {

		}
	}
	
}
