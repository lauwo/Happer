/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Playfield.java
 *
 * Created on Nov 18, 2011, 2:42:20 PM
 */
package View;

import Components.Direction;
import Model.Field;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import Model.Happer;
import Model.Human;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Laurens
 */
public class Playfield extends javax.swing.JPanel {

	Dimension playFieldSize = new Dimension(20, 20);
    ArrayList<Field> fields = new ArrayList<Field>();
	Human human;
	
	/** Creates new form Playfield */
	public Playfield() {
		initComponents();
		setLayout(null);		
        setPreferredSize(new Dimension(playFieldSize.width * Field.width, playFieldSize.height * Field.height));

		setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
        this.setFocusable(true);
	}
	
	public Field getRandomfield() {
		int random = (int)Math.round(Math.random() * fields.size());
		Field randomField = fields.get(random);
		
		if (randomField.getGameObject() == null)
			return fields.get(random);
		
		return getRandomfield();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		human = new Human(getRandomfield());
		addKeyListener(human);
		human.Draw(g);			
	}
	
    protected void paintBackground(Graphics g) {
		for(int i =0; i < playFieldSize.width; i++){
			for(int j =0; j < playFieldSize.height; j++){
				int posX = i * Field.width;
				int posY = j * Field.height;
				g.drawRect(posX, posY, Field.width, Field.height);
				if (fields.size() < (playFieldSize.width * playFieldSize.height)) {
					Field newField = new Field(posX, posY, this);
					fields.add(newField);			
				}
			}
		}
		if (fields.size() < (playFieldSize.width * playFieldSize.height)) {
			for (Field field : fields) {
				setNeighbourFields(field);
			}
		}
    }
	
	public void setNeighbourFields(Field newField) {
		int currentIndex = fields.indexOf(newField);
		int left = currentIndex - 1;
		int right = currentIndex + 1;
		int top = currentIndex - playFieldSize.height;
		int down = currentIndex + playFieldSize.height;
		int totalFields = playFieldSize.width * playFieldSize.height;
		
		if (currentIndex % playFieldSize.width == 0) {
			left = -1;
		} else if (currentIndex % (playFieldSize.width - 1) == 0) {
			right = -1;
		} else if (currentIndex >= (totalFields - playFieldSize.width)) {
			down = -1;
		} else if (currentIndex <= playFieldSize.width) {
			top = -1;
		}
		
		if (top >= 0 && top < totalFields)
			newField.setNeighbourField(Direction.UP, fields.get(top));
		if (down >= 0 && down < totalFields)
			newField.setNeighbourField(Direction.DOWN, fields.get(down));
		if (left >= 0 && left < totalFields)
			newField.setNeighbourField(Direction.LEFT, fields.get(left));
		if (right >= 0 && right < totalFields)
			newField.setNeighbourField(Direction.RIGHT, fields.get(right));
	}
	

    @Override
    public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h)
	{
        super.imageUpdate(img, flags, x, y, w, h);
        repaint();
        return true;
    }
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
