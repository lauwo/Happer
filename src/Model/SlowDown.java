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
public class SlowDown extends PowerUp {
	
	/**
	 * creates a new slowdown powerup
	 * @param field the field the slowdown should be located upon
	 */
	public SlowDown(Field field) {
		super(field);
		field.setGameObject(this);
		try {
			BufferedImage image = ImageIO.read(new File("images/lekkeband.png"));
			super.setImage(image);
		} catch (IOException ex) {

		}
	}
}
