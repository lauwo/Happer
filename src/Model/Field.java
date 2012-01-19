/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Laurens
 */
public class Field {
	private int posX;
	private int posY;
	public static int width = 26;
	public static int height = 26;
	private GameObject gameObject;
	private HashMap<Direction, Field> neighbourFields;
	
	/**
	 * creates a field
	 * @param posX the x position that the field is located on
	 * @param posY the y position that the field is located on
	 */
	public Field(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.neighbourFields = new HashMap<Direction, Field>();
	}

	/**
	 * retrieves the current x position of the field
	 * @return the current x position of the field
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * sets the x position for the field
	 * @param posX the new x position that the field should be located on
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * retrieves the current y position of the field
	 * @return the current y position of the field
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * sets the y position for the field
	 * @param posY the new y position that the field should be located on
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * retrieves the current GameObject that is located on the field
	 * @return the field's current GameObject
	 */
	public GameObject getGameObject() {
		return gameObject;
	}
	
	
	/**
	 * sets the fields GameObject
	 * @param gameObject the new GameObject that should be on the field
	 */
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	/**
	 * checks to see whether the field holds a GameObject
	 * @return true if the field holds a GameObject
	 */
	public boolean hasGameObject() {
		return gameObject != null;
	}
	
	/**
	 * checks to see whether this field is walkable by the happer
	 * @return true if the field does not hold a GameObject or if the GameObject is a powerup
	 */
	public boolean isWalkable() {
		return gameObject == null || gameObject instanceof PowerUp;
	}
	
	/**
	 * sets a neighbourField for the field
	 * @param direction the direction that the field should is located at
	 * @param field the neighbourField
	 */
	public void setNeighbourField(Direction direction, Field field) {
		if (!neighbourFields.containsKey(direction))
			neighbourFields.put(direction, field);
	}
	
	/**
	 * retrieves a neigbourField in a given direction
	 * @param direction the direction which to get the field from
	 * @return the neigbourField in the given direction
	 */
	public Field getNeighbourField(Direction direction) {
		return neighbourFields.get(direction);
	}

	/**
	 * gets the neighbourFields of the field
	 * @return all of the field's neighbourFields
	 */
	public HashMap<Direction, Field> getNeighbourFields() {
		return neighbourFields;
	}
	
	/**
	 * retrieves the direction in which a direct neighbourField is located
	 * @param neighbourField the neighbourField to check the direction of
	 * @return the direction in which this field is a direct neighbour -- null if the given field is not a direct neighbour
	 */
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
	
	/**
	 * retrieves the direct neighbours that do not hold a gameobject
	 * @return an arraylist that holds all the direct neighbours without a gameobject on them
	 */
	public ArrayList<Field> getEmptyNeighbourFields() {
		ArrayList<Field> emptyNeighbours = new ArrayList<Field>();		
		Iterator it = neighbourFields.values().iterator();
		
		while (it.hasNext()) {
			Field neighbourField = (Field)it.next();
			if (!neighbourField.hasGameObject()) {
				emptyNeighbours.add(neighbourField);
			}
		}
		
		return emptyNeighbours;
	}
}
