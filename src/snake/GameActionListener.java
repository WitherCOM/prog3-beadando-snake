package snake;

import java.util.List;

public interface GameActionListener {
	public void updated();
	public void snakeEatenFood(List<Snake> snakes);
	public void gameOver(Snake winner);
	public void gameTimeTick(int currentTime);
}
