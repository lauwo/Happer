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
import Model.Box;
import Model.Field;
import Model.Game;
import Model.GameObject;
import Model.Human;
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
	private Human human;
	private boolean currentlyMoving;
	private boolean walls;
	private BufferedImage road;
	private BufferedImage grass;
	private BufferedImage grassTop;
	private BufferedImage grassUnder;
	private BufferedImage grassRight;
	private BufferedImage grassLeft;

	/**
	 * create a new playfield
	 * @param dimension the dimension of the playfield
	 * @param walls true if there should be walls, false if the field shouldnt have walls
	 */
	public Playfield(int dimension, boolean walls) {
		initComponents();
		playfieldDimension = dimension;
		this.walls = walls;
		this.currentlyMoving = false;
		setLayout(null);
		rows = new ArrayList<ArrayList<Field>>();
        setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
		this.setFocusable(true);
		addKeyListener(this);
		initiatePlayfield();
		loadImages();		
	}

	/*
	 * initializes the playfield
	 */
	private void initiatePlayfield() {
		initFields();
		setNeighbourFields();
	}
	
	/*
	 * load the images needed for display (so this is only done once for performance reasons)
	 */
	private void loadImages() {
		try {
			 road = ImageIO.read(new File("images/grond/asfalt.png"));
			 grass = ImageIO.read(new File("images/grond/gras.png"));
			 grassTop = ImageIO.read(new File("images/grond/grasboven.png"));
			 grassUnder = ImageIO.read(new File("images/grond/grasonder.png"));
			 grassRight = ImageIO.read(new File("images/grond/grasrechts.png"));
			 grassLeft = ImageIO.read(new File("images/grond/graslinks.png"));
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	/**
	 * add the game objects to the playfield (boxes & rocks)
	 * @param boxPercentage the percentage of boxes on the playfield
	 * @param rockPercentage the percentage of rocks on the playfield
	 */
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
	 * adds the human to the game (this can only be done once, else you will simply replace the old one)
	 * @param game the game the human is being added to
	 */
	public void addHuman(Game game) {
		human = new Human(getRandomEmptyField(), game);
	}
	
	/**
	 * retrieve the human
	 * @return the human
	 */
	public Human getHuman() {
		return human;
	}
	
	/**
	 * Gets a random field that does not have a game object on it anywhere on the playfield 
	 * @return a random empty field on the playfield
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
	
	
	/**
	 * creates the fields and add them to an arraylist
	 */
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
	/**
	 * sets the neighbourfields that every field has
	 */
	public void setNeighbourFields() {
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {				
				int currentRow = rows.indexOf(row);
				int currentField = row.indexOf(field);
				Field up = getField(currentRow - 1, currentField);
				Field down = getField(currentRow + 1, currentField);
				Field left = getField(currentRow, currentField - 1);
				Field right = getField(currentRow, currentField + 1);
				
				if (!walls) {
					if (up == null)
						up = getField(rows.size() - 1, currentField);
					
					if (down == null)
						down = getField(0, currentField);
					
					if (left == null)
						left = getField(currentRow, row.size() - 1);
					
					if (right == null)
						right = getField(currentRow, 0);
				}
				
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
	
	/*
	 * gets a field at a current position within the arraylist
	 * @return the requested field if it is within the playfield's boundaries
	 */
	public Field getField(int x, int y) {
		if (x < rows.size() && x >= 0) {
			if (y < rows.get(x).size() && y >= 0) {
				return rows.get(x).get(y);
			}
		}
 		return null;
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
	}

	protected void paintBackground(Graphics g) {
		for (ArrayList<Field> row : rows) {
			for (Field field : row) {
				g.drawImage(road, field.getPosX(), field.getPosY(), this);
				if (field.hasGameObject()) {
					if (field.getGameObject() instanceof Rock) {								
						g.drawImage(grass, field.getPosX(), field.getPosY(), this);
					}								
				}
				for (Field neighbour : field.getNeighbourFields().values()) {
					if (neighbour.hasGameObject()) {
						if (neighbour.getGameObject() instanceof Rock) {
							Image grassToApply = null;
							int x = field.getPosX();
							int y = field.getPosY();
							switch (field.getNeighbourDirection(neighbour)) {
								case UP:
									grassToApply = grassTop;										
									break;
								case DOWN:
									grassToApply = grassUnder;
									y = y + 23;
									break;
								case LEFT:
									grassToApply = grassLeft;
									break;
								case RIGHT:
									grassToApply = grassRight;
									x = x + 23;
									break;
							}
							g.drawImage(grassToApply, x, y, this);
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

	/**
	 * this method is called whenever the actor first presses and then releases a key
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	/**
	 * this method gets called whenever the actor presses a key
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!currentlyMoving) {
			currentlyMoving = true;
			 switch(e.getKeyCode())	{
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
	}

	/**
	 * this method is called whenever the actor releases a key
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		currentlyMoving = false;
	}
}
