/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
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
	
	private String image;
	private Field field;
	
	public GameObject(Field field, String imageUrl) {
		this.field = field;
		this.image = imageUrl;
	}
	
	public void Draw(Graphics g){		
//		try {
//			Image img = ImageIO.read(new File(this.image));
//			int width = Field.width;
//			int height = Field.height;
//			int frame = 1;
//			int frameX = (frame % 3) * width;
//			int frameY = (frame / 3) * width;
//			//g.drawImage(img, field.getPosX(), field.getPosY(), field.getPosX()+width, field.getPosY()+height, frameX, frameY, frameX+width, frameY+height, field.getPlayField());
//			g.drawImage(img, 16, 16, field.getPlayField());
//		} catch (IOException ex) {
//			System.out.println(ex);
//		}
		
		if (field.getGameObject() instanceof Box)
			g.setColor(Color.BLUE);
		else if (field.getGameObject() instanceof Happer)
			g.setColor(Color.RED);
		else if (field.getGameObject() instanceof Rock)
			g.setColor(Color.BLACK);
		else if (field.getGameObject() instanceof Human)
			g.setColor(Color.GREEN);
		else if (field.getGameObject() instanceof ImmunityShield)
			g.setColor(Color.YELLOW);
		else if (field.getGameObject() instanceof SlowDown)
			g.setColor(Color.DARK_GRAY);
		
		g.fillRect(getField().getPosX(), getField().getPosY(), getField().width, getField().height);
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
