package snake;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		
	}
	
	@Test
	public void test_add_food()
	{
		
	}
	
	@Test
	public void test_remove_food()
	{
		
	}
	
	@Test
	public void test_set_game_mode()
	{
		
	}

}
