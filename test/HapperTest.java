/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Field;
import Model.Human;
import Model.Happer;
import View.Gameframe;
import Model.Game;
import View.Playfield;
import Components.Direction;
import Model.Pathfinder;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Laurens
 */
public class HapperTest {
	
	public HapperTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
	
	@Test
	public void testPathfinder() {
		Playfield playfield = new Playfield(5, false);
		Human human = new Human(playfield.getField(1, 1), null);
		Happer happer = new Happer(playfield.getField(1, 4), null, 10000);
		happer.getTimer().stop();
		//look at positionering test.png to see how the human (green) and the happer(red) are positioned.
		Assert.assertTrue(Pathfinder.findShortestPath(happer.getField()) == Direction.RIGHT);
	}
	
	@Test
	public void testHumanMove() {
		Game game = new Game(new Gameframe());
		Playfield playfield = new Playfield(5, false);
		game.setPlayfield(playfield);
		Human human = new Human(playfield.getField(1, 1), game);
		Field field = human.getField();
		human.move(Direction.RIGHT);
		Assert.assertTrue(field == human.getField().getNeighbourField(Direction.LEFT));
		field = human.getField();
		human.move(Direction.DOWN);
		Assert.assertTrue(field == human.getField().getNeighbourField(Direction.UP));
		field = human.getField();
		human.move(Direction.LEFT);
		Assert.assertTrue(field == human.getField().getNeighbourField(Direction.RIGHT));
		field = human.getField();
		human.move(Direction.UP);
		Assert.assertTrue(field == human.getField().getNeighbourField(Direction.DOWN));
	}
	
	@Test
	public void testHapperMove() {
		Game game = new Game(new Gameframe());
		Playfield playfield = new Playfield(5, false);
		game.setPlayfield(playfield);
		Happer happer = new Happer(playfield.getField(1, 1), game, 10000);
		happer.getTimer().stop();
		Field field = happer.getField();
		happer.move(Direction.RIGHT);
		Assert.assertTrue(field == happer.getField().getNeighbourField(Direction.LEFT));
		field = happer.getField();
		happer.move(Direction.DOWN);
		Assert.assertTrue(field == happer.getField().getNeighbourField(Direction.UP));
		field = happer.getField();
		happer.move(Direction.LEFT);
		Assert.assertTrue(field == happer.getField().getNeighbourField(Direction.RIGHT));
		field = happer.getField();
		happer.move(Direction.UP);
		Assert.assertTrue(field == happer.getField().getNeighbourField(Direction.DOWN));
	}
}
