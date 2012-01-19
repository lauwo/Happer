/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Laurens
 */
public abstract class GameObject {
	
	private String image;
	private Field field;
	
	/**
	 * creates a new GameObject
	 * @param field the field that the GameObject should be placed upon
	 * @param imageUrl the url to the image that the GameObject should have
	 */
	public GameObject(Field field, String imageUrl) {
		this.field = field;
		this.image = imageUrl;
	}

	/**
	 * retrieves the field that the gameobject is located on
	 * @return the field that the gameobject is currently located on
	 */
	public Field getField() {
		return field;
	}

	/**
	 * sets the field that the gameobject is located on
	 * @param field the field that the gameobject should be placed upon
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * retrieves the imageurl for the gameobject
	 * @return the current imageurl for the gameobject
	 */
	public String getImage() {
		return image;
	}

	/**
	 * sets the imageurl for the gameobject
	 * @param image the new imageurl for the gameobject
	 */
	public void setImage(String image) {
		this.image = image;
	}
}
