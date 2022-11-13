package snake;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import snake.GameData.GameMode;
import snake.Snake.Direction;
import snake.Snake.InputLayout;

public class GameDataTest {

	GameData data;
	
	@Before
	public void setup()
	{
		data = new GameData();
	}
	
	@Test
	public void test_construct()
	{
		assertNull(data.getGameMode());
		assertArrayEquals(new Object[] {}, data.getFoods().toArray());
		assertArrayEquals(new Object[] {}, data.getSnakes().toArray());

	}
	
	@Test
	public void test_add_snake()
	{
		assertEquals(0, data.getSnakes().size());
		data.addSnake(new Snake(2, 2, 5, 20,Direction.RIGHT, new InputLayout(KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0)));
		assertEquals(1, data.getSnakes().size());
	}
	
	@Test
	public void test_add_food()
	{
		assertEquals(0, data.getFoods().size());
		data.addFood(new Food(2,2));
		data.addFood(new Food(2,3));
		assertEquals(2, data.getFoods().size());
	}
	
	@Test
	public void test_remove_food()
	{
		Food food = new Food(2,2);
		assertEquals(0, data.getFoods().size());
		data.addFood(food);
		assertEquals(1, data.getFoods().size());
		data.removeFood(food);
		assertEquals(0, data.getFoods().size());
	}

}
