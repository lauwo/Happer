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

	Dimension playFieldSize;
    ArrayList<ArrayList<Field>> rows;
	Human human;
	Happer happer;
	
	public Playfield(int dimension) {
		initComponents();
		setLayout(null);
		rows = new ArrayList<ArrayList<Field>>();
		playFieldSize = new Dimension(dimension, dimension);
        setPreferredSize(playFieldSize);
        setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
		this.setFocusable(true);
		addKeyListener(this);		
		Logger.log("Playfield loaded.");
		initiatePlayfield();
		happer.checkHumanField();
	}

	private void initiatePlayfield() {
		initFields();
		setNeighbourFields();
		addObjects(8, 10);
		addHuman();
		addHapper();
	}
	
	private void addObjects(int rockPercentage, int boxPercentage) {
		int boxesPerRow = (int)Math.round(((double)playFieldSize.width / 100) * boxPercentage);		
		int rocksPerRow = (int)Math.round(((double)playFieldSize.width / 100) * rockPercentage);
		
		int total = boxesPerRow + rocksPerRow;
		
		for (ArrayList<Field> row : rows) {
			Collections.shuffle(row);
			for (int i = 0; i < total; i++) {
				Field currentField = row.get(i);
				if (i <= boxesPerRow) {
					currentField.setGameObject(new Box(currentField));
				} else {
					currentField.setGameObject(new Rock(currentField));
				}
			}
		}		
		
	}
	
	private Field getRandomField()	{
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

	private void addHuman() {				
		human = new Human(getRandomField());
	}
	
	private void addHapper() {
		happer = new Happer(getRandomField());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
	}
	
	public void initFields() {
		for (int i = 0; i < playFieldSize.width; i++){
			ArrayList<Field> row = new ArrayList<Field>();
			for (int j = 0; j < playFieldSize.height; j++) {
				int posX = j * Field.width;
				int posY = i * Field.height;
				Field newField = new Field(posX, posY, this);
				row.add(newField);			
			}			
			rows.add(row);
		}
	}
	
    protected void paintBackground(Graphics g) {
		g.setColor(Color.BLACK);
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {
				if (field.getGameObject() != null) {
					if (field.getGameObject() instanceof Box)
						g.setColor(Color.BLUE);
					else if (field.getGameObject() instanceof Happer)
						g.setColor(Color.RED);
					else if (field.getGameObject() instanceof Rock)
						g.setColor(Color.BLACK);
					else if (field.getGameObject() instanceof Human)
						g.setColor(Color.GREEN);

					g.fillRect(field.getPosX(), field.getPosY(), Field.width, Field.height);			
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

	@Override
	public void keyTyped(KeyEvent ke) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                 human.move(Direction.DOWN);
                break;
            case KeyEvent.VK_UP:
				human.move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                 human.move(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                 human.move(Direction.LEFT);
                break;
        default:
            break;
        }   
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}
}
