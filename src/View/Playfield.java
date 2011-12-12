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
import Components.Logger;
import Model.Box;
import Model.Field;
import Model.Game;
import Model.Happer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import Model.Human;
import Model.Rock;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Laurens
 */
public class Playfield extends javax.swing.JPanel implements KeyListener {

	private Dimension amountOfFields;
    public ArrayList<ArrayList<Field>> rows;
	private Game game;
	
	public Playfield(int dimension, Game game) {
		this.game = game;
		initComponents();
		setLayout(null);
		rows = new ArrayList<ArrayList<Field>>();
		amountOfFields = new Dimension(dimension, dimension);
        setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
		this.setFocusable(true);
		addKeyListener(this);		
		Logger.log("Playfield loaded.");
		initiatePlayfield();
	}

	private void initiatePlayfield() {
		initFields();
		setNeighbourFields();
	}
	
	public void addGameObjects(int boxPercentage, int rockPercentage) {
		int boxesPerRow = (int)Math.round(((double)amountOfFields.width / 100) * boxPercentage);		
		int rocksPerRow = (int)Math.round(((double)amountOfFields.width / 100) * rockPercentage);
		
		int total = boxesPerRow + rocksPerRow;
		
		for (ArrayList<Field> row : rows) {
			Collections.shuffle(row);
			for (int i = 0; i < total; i++) {
				Field currentField = row.get(i);
				if (i < boxesPerRow) {
					currentField.setGameObject(new Box(currentField));
				} else {
					currentField.setGameObject(new Rock(currentField));
				}
			}
		}		
		
	}
	
	public Field getRandomField()	{
		int random = (int)Math.round(Math.random() * (rows.size() - 1)) + 1;		
		ArrayList<Field> randomRow = rows.get(random - 1);
		Field randomField = null;
		
		while (randomField == null) {
			random = (int)Math.round(Math.random() * (randomRow.size() - 1)) + 1;
			Field field = randomRow.get(random - 1);
			if (field.getGameObject() == null)
				randomField = field;
		}
		return randomField;		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
	}
	
	public void initFields() {
		for (int i = 0; i < amountOfFields.width; i++){
			ArrayList<Field> row = new ArrayList<Field>();
			for (int j = 0; j < amountOfFields.height; j++) {
				int posX = j * Field.width;
				int posY = i * Field.height;
				Field newField = new Field(posX, posY, this);
				row.add(newField);			
			}			
			rows.add(row);
		}
	}
	
    protected void paintBackground(Graphics g) {
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {
				if (field.getGameObject() != null) {
					field.getGameObject().Draw(g);
				}
			}
		}
    }
	
	public void setNeighbourFields() {
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {				
				int currentRow = rows.indexOf(row);
				int currentField = row.indexOf(field);
				Field up = getField(currentRow - 1, currentField);
				Field down = getField(currentRow + 1, currentField);
				Field left = getField(currentRow, currentField - 1);
				Field right = getField(currentRow, currentField + 1);
				
				if (up != null)
					field.setNeighbourField(Direction.UP, up);
				
				if (down != null)
					field.setNeighbourField(Direction.DOWN, down);
				
				if (left != null)
					field.setNeighbourField(Direction.LEFT, left);
				
				if (right != null)
					field.setNeighbourField(Direction.RIGHT, right);
			}
		}
	}
	
	private Field getField(int x, int y) {
		if (x < rows.size() && x >= 0) {
			if (y < rows.get(x).size() && y >= 0) {
				return rows.get(x).get(y);
			}
		}
 		return null;
	}

	public Game getGame() {
		return game;
	}	

    @Override
    public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
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

	@Override
	public void keyTyped(KeyEvent ke) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                 game.getHuman().move(Direction.DOWN);
                break;
            case KeyEvent.VK_UP:
				game.getHuman().move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                 game.getHuman().move(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                 game.getHuman().move(Direction.LEFT);
                break;
        default:
            break;
        }   
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}
}
