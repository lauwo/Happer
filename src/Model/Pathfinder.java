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
	
	private Pathfinder(){};
	
	public static Direction findShortestPath(Field currentField) {		
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
						if (!neighbourField.hasGameObject() || neighbourField.getGameObject() instanceof Human) {
							ArrayList<Field> newRoute = new ArrayList<Field>(shortestRoute);
							newRoute.add(neighbourField);
							possibleRoutes.add(newRoute);
							if (neighbourField.getGameObject() instanceof Human) {
								humanRouteFound = true;	
								return currentField.getNeighbourDirection(newRoute.get(1));
							}
						}
						checkedFields.add(neighbourField);
					}
				}
				possibleRoutes.remove(shortestRoute);			
			}
		}		
		
		ArrayList<Field> emptyNeighbourFields = currentField.getEmptyNeighbourFields();
		if (!emptyNeighbourFields.isEmpty()) {
			int random = (int)(Math.random() * emptyNeighbourFields.size()) + 1;
			return currentField.getNeighbourDirection(emptyNeighbourFields.get(random - 1));
		} else {
			return null;
		}
	}	
}
