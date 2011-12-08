/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import View.Playfield;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Laurens
 */
public class Field {

	private int posX;
	private int posY;
	public static int width = 24;
	public static int height = 24;
	
	private Playfield playfield;
	
	private GameObject gameObject;
	private HashMap<Direction, Field> neighbourFields;
	
	public Field(int posX, int posY, Playfield playfield) {
		this.posX = posX;
		this.posY = posY;
		this.neighbourFields = new HashMap<Direction, Field>();
		this.playfield = playfield;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
	
	public boolean hasGameObject() {
		return gameObject != null;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public void setNeighbourField(Direction direction, Field field) {
		if (!neighbourFields.containsKey(direction))
			neighbourFields.put(direction, field);
	}
	
	public Field getNeighbourField(Direction direction) {
		return neighbourFields.get(direction);
	}
	
	public Playfield getPlayField() {
		return this.playfield;
	}

	public HashMap<Direction, Field> getNeighbourFields() {
		return neighbourFields;
	}
	
	public Direction getNeighbourDirection(Field neighbourField) {
		Iterator it = neighbourFields.keySet().iterator();
		
		while (it.hasNext()) {
			Direction direction = (Direction)it.next();
			if (neighbourFields.get(direction).equals(neighbourField)) {
				return direction;
			}			
		}
		
		return null;
	}
}
