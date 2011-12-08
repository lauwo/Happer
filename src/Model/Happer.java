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
	
	Pathfinder x = new Pathfinder();
	public Happer(Field field) {
		super(field);
		new Timer(500, taskPerformer).start();
	}
	
	public void checkHumanField() {
		
	}
	
		
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			//int direction = (int)(Math.random() * 3);
			move(x.findShortestPath(field));
		}
	};
	
}
