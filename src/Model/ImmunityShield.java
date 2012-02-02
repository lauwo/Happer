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
public class ImmunityShield extends PowerUp {
	
	/**
	 * creates a new immunityshield
	 * @param field the field that the shield should be placed on
	 */
	public ImmunityShield(Field field) {
		super(field);
		field.setGameObject(this);
		try {
			BufferedImage image = ImageIO.read(new File("images/shield.png"));
			super.setImage(image);
		} catch (IOException ex) {

		}
	}
}
