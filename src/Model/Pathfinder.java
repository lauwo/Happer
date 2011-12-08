/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Components.Direction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Laurens
 */
public class Pathfinder {
	
	public Direction findShortestPath(Field currentField) {
		
		ArrayList<ArrayList<Field>> possibleRoutes = new ArrayList<ArrayList<Field>>();
		HashSet<Field> checkedFields = new HashSet<Field>();
				
		boolean humanRouteFound = false;
		
		ArrayList<Field> beginRoute = new ArrayList<Field>();
		beginRoute.add(currentField);
		possibleRoutes.add(beginRoute);
		
		while (!humanRouteFound && possibleRoutes.size() > 0) {		
			ArrayList<Field> shortestRoute = new ArrayList<Field>(possibleRoutes.get(0));
			for (ArrayList<Field> route : possibleRoutes) {
				if (route.size() < shortestRoute.size())
					shortestRoute = route;
			}

			for (Field field : shortestRoute) {
				Iterator it = field.getNeighbourFields().values().iterator();

				while (it.hasNext()) {
					Field neighbourField = (Field)it.next();
					if (!checkedFields.contains(neighbourField)) {
						if (!neighbourField.hasGameObject()) {
							ArrayList<Field> newRoute = new ArrayList<Field>(shortestRoute);
							newRoute.add(neighbourField);
							possibleRoutes.add(newRoute);
						} else if (neighbourField.getGameObject() instanceof Human) {
							ArrayList<Field> newRoute = new ArrayList<Field>(shortestRoute);
							newRoute.add(neighbourField);
							possibleRoutes.add(newRoute);
							humanRouteFound = true;	
							System.out.println(currentField.getNeighbourDirection(newRoute.get(1)).name());
							return currentField.getNeighbourDirection(newRoute.get(1));
						}
						checkedFields.add(neighbourField);
					}
				}
				possibleRoutes.remove(shortestRoute);			
			}
		}
		
		int direction = (int)(Math.random() * 3);
 		return Direction.values()[direction];
	}

}
