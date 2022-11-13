package snake;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

import org.junit.Before;
import org.junit.Test;

import snake.GameData.GameMode;
import snake.Snake.Direction;
import snake.Snake.InputLayout;

public class GameViewTest {

	GameView gameView;
	
	@Before
	public void setup()
	{
		gameView = new GameView();
	}
	
	@Test
	public void test_set_model() {
		GameData model = new GameData();
		model.addSnake(new Snake(2, 2, 5, 20,Direction.RIGHT, new InputLayout(KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0)));
		gameView.setModel(model);
		assertEquals(gameView.getActionMap().allKeys().length, 6);
		gameView.setModel(model);
		assertEquals(gameView.getActionMap().allKeys().length, 6);
	}
	
	@Test
	public void test_get_frame_dimension() {
		GameData model = new GameData();
		model.setGameMode(new GameMode("EASY",20,20,20,20));
		model.addSnake(new Snake(2, 2, 5, 20,Direction.RIGHT, new InputLayout(KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0)));
		gameView.setModel(model);
		assertEquals(new Dimension(20*20,20*20), gameView.getFrameDimension());
	}

}
