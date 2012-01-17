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
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import Model.Rock;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

/**
 *
 * @author Laurens
 */
public class Playfield extends javax.swing.JPanel implements KeyListener {

	private int playfieldDimension;
    public ArrayList<ArrayList<Field>> rows;
	private Game game;
	boolean currentlyMoving = false;
	
	public Playfield(int dimension, Game game) {
		this.game = game;
		initComponents();
		playfieldDimension = dimension;
		setLayout(null);
		rows = new ArrayList<ArrayList<Field>>();
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
		int boxesPerRow = (int)Math.round(((double)playfieldDimension / 100) * boxPercentage);		
		int rocksPerRow = (int)Math.round(((double)playfieldDimension / 100) * rockPercentage);
		
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
	/**
	 * Gets a random field that does not have a game object on it somewhere on the playfield 
	 * @return Field if a random empty field could be found
	 */
	
	public Field getRandomEmptyField()	{
		int random = (int)Math.round(Math.random() * (rows.size() - 1)) + 1;		
		ArrayList<Field> randomRow = rows.get(random - 1);

		random = (int)Math.round(Math.random() * (randomRow.size() - 1)) + 1;
		Field field = randomRow.get(random - 1);
		if (!field.hasGameObject()) {
			return field;
		}
	
		return getRandomEmptyField();		
	}
	
	
	public void initFields() {
		for (int i = 0; i < playfieldDimension; i++){
			ArrayList<Field> row = new ArrayList<Field>();
			for (int j = 0; j < playfieldDimension; j++) {
				int posX = j * Field.width;
				int posY = i * Field.height;
				Field newField = new Field(posX, posY);
				row.add(newField);			
			}			
			rows.add(row);
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
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
	}
	
    protected void paintBackground(Graphics g) {
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {
					try {
						BufferedImage road = ImageIO.read(new File("images/grond/asfalt.png"));
						g.drawImage(road, field.getPosX(), field.getPosY(), this);
					} catch (IOException ex) {
						System.out.println(ex);
					}
				if (field.hasGameObject()) {
					try {
						if (field.getGameObject() instanceof Rock) {
							BufferedImage grass = ImageIO.read(new File("images/grond/gras.png"));
							g.drawImage(grass, field.getPosX(), field.getPosY(), this);
						}				
					} catch (IOException ex) {
						System.out.println(ex);
					}					
				}
				for (Field neighbour : field.getNeighbourFields().values()) {
					if (neighbour.hasGameObject()) {
						if (neighbour.getGameObject() instanceof Rock) {
							try {
								String grassToApply = "";
								int x = field.getPosX();
								int y = field.getPosY();
								switch (field.getNeighbourDirection(neighbour)) {
									case UP:
										grassToApply = "images/grond/grasboven.png";										
										break;
									case DOWN:
										grassToApply = "images/grond/grasonder.png";
										y = y + 23;
										break;
									case LEFT:
										grassToApply = "images/grond/graslinks.png";
										break;
									case RIGHT:
										grassToApply = "images/grond/grasrechts.png";
										x = x + 23;
										break;
								}
								BufferedImage grass = ImageIO.read(new File(grassToApply));
								g.drawImage(grass, x, y, this);
							} catch (IOException ex) {
								System.out.println(ex);
							}
						}
					}			
				}
				if (field.hasGameObject()) {
					try {
						BufferedImage img = ImageIO.read(new File(field.getGameObject().getImage()));
						g.drawImage(img, field.getPosX(), field.getPosY(), this);
					} catch (IOException ex) {

					}
				}
			}
		}
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
	public void keyTyped(KeyEvent e) {
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!currentlyMoving) {
			currentlyMoving = true;
			 switch(e.getKeyCode())	{
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentlyMoving = false;
	}
}
