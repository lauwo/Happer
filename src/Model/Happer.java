/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Laurens
 */
public class Happer extends MoveableObject {
	
	public Happer(Field field) {
		super(field);
		new Timer(500, taskPerformer).start();
	}
	
	public void checkHumanField() {
		
	}
	
		
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			Direction moveDirection = Pathfinder.findShortestPath(field);
			if (field.getNeighbourField(moveDirection).getGameObject() instanceof Human) {
				System.out.println("Human is: " + moveDirection.name());
			}
			move(moveDirection);
		}
	};
	
}
