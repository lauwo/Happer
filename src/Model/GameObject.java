/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Laurens
 */
public abstract class GameObject {
	
	String image;
	Field field;
	
	public GameObject(Field field, String imageUrl) {
		this.field = field;
		this.image = imageUrl;
	}
	
	public void Draw(Graphics g){
		try {
			Image img = ImageIO.read(new File(this.image));
			int width = Field.width;
			int height = Field.height;
			int frame = 1;
			int frameX = (frame % 3) * width;
			int frameY = (frame / 3) * width;
			//g.drawImage(img, field.getPosX(), field.getPosY(), field.getPosX()+width, field.getPosY()+height, frameX, frameY, frameX+width, frameY+height, field.getPlayField());
			g.drawImage(img, 16, 16, field.getPlayField());
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
}
