package snake;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import snake.Snake.Direction;
import snake.Snake.InputLayout;

public class SnakeTest {

	Snake snake;
	@Before
	public void setup()
	{
		snake = new Snake(2,2,5,20,Direction.UP,new InputLayout(KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I));
	}
	
	@Test
	public void test_construct() {
		assertEquals(5, snake.getBodyPoints().size());
		assertEquals(2, snake.getHeadX());
		assertEquals(2, snake.getHeadY());
		assertEquals(new InputLayout(KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I),snake.getControl());
		assertArrayEquals(new Object[] {new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6),new Point(2,7)}, snake.getBodyPoints().toArray());
	}
	
	@Test
	public void test_increase_length()
	{
		snake.increaseLength();
		assertEquals(6,snake.getBodyPoints().size());
		assertArrayEquals(new Object[] {new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6),new Point(2,7),new Point(2,2)}, snake.getBodyPoints().toArray());
		snake.move();
		assertEquals(6,snake.getBodyPoints().size());
		assertArrayEquals(new Object[] {new Point(2,2),new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6),new Point(2,7)}, snake.getBodyPoints().toArray());
	}
	
	@Test
	public void test_simple_move()
	{
		assertEquals(2, snake.getHeadX());
		assertEquals(2, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6),new Point(2,7)}, snake.getBodyPoints().toArray());
		
		snake.move();
		assertEquals(2, snake.getHeadX());
		assertEquals(1, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(2,2),new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6)}, snake.getBodyPoints().toArray());
		
		snake.move();
		assertEquals(2, snake.getHeadX());
		assertEquals(0, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(2,1),new Point(2,2),new Point(2,3),new Point(2,4),new Point(2,5)}, snake.getBodyPoints().toArray());
		
		snake.move();
		assertEquals(2, snake.getHeadX());
		assertEquals(19, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(2,0),new Point(2,1),new Point(2,2),new Point(2,3),new Point(2,4)}, snake.getBodyPoints().toArray());
	}
	
	@Test
	public void test_complex_move()
	{
		assertEquals(2, snake.getHeadX());
		assertEquals(2, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(2,3),new Point(2,4),new Point(2,5),new Point(2,6),new Point(2,7)}, snake.getBodyPoints().toArray());
		
		//Move and turn
		snake.move(); //(2,1)
		snake.turnDown(); //Fake move
		snake.turnLeft();
		snake.move(); //(1,1)
		snake.turnDown();
		snake.move(); // (1,2)
		snake.turnLeft();
		snake.move(); // (0,2)
		snake.move(); // (19,2)
		assertEquals(19, snake.getHeadX());
		assertEquals(2, snake.getHeadY());
		assertArrayEquals(new Object[] {new Point(0,2),new Point(1,2),new Point(1,1),new Point(2,1),new Point(2,2)}, snake.getBodyPoints().toArray());
	}
	
	@Test
	public void test_hit_food()
	{		
		assertEquals(0, snake.getScore());
		
		assertTrue(snake.hitFood(new Food(2,2)));
		assertEquals(1, snake.getScore());
		assertEquals(6,snake.getBodyPoints().size());
		
		assertFalse(snake.hitFood(new Food(2,3)));
		assertEquals(1, snake.getScore());
		assertEquals(6,snake.getBodyPoints().size());
		
		assertFalse(snake.hitFood(new Food(3,3)));
		assertEquals(1, snake.getScore());
		assertEquals(6,snake.getBodyPoints().size());
	}
	
	@Test
	public void test_hit_snake()
	{
		Snake snake2 = new Snake(2,3,5,20,Direction.LEFT,new InputLayout(KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I,KeyEvent.VK_I));
		assertTrue(snake2.hitSnake(snake));
		assertFalse(snake.hitSnake(snake2));
	}

}

