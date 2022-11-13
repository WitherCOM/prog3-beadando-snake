package snake;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import snake.GameData.GameMode;

public class GameModeComboBoxModelTest {

	GameModeComboBoxModel model;
	List<GameMode> gameModes;
	
	@Before
	public void setup()
	{
		gameModes = new ArrayList<GameMode>();
		gameModes.add(new GameMode("EASY",10,10,10,10));
		gameModes.add(new GameMode("MED",10,10,10,10));
		gameModes.add(new GameMode("HARD",10,10,10,10));
		model = new GameModeComboBoxModel(gameModes);
	}
	
	@Test
	public void test_construct()
	{
		assertEquals(gameModes.get(0), model.getSelectedItem());
	}
	
	@Test
	public void test_get_element_at()
	{
		assertEquals(gameModes.get(2), model.getElementAt(2));
	}
	
	@Test
	public void test_selected_item()
	{
		model.setSelectedItem(model.getElementAt(2));
		assertEquals(gameModes.get(2), model.getSelectedItem());
	}

}
