package snake;

import java.util.List;

public interface GameActionListener {
	/**
	 * Called when something is updated
	 */
	public void updated();
	/**
	 * Called when a food is eaten
	 * 
	 * @param snakes
	 */
	public void snakeEatenFood(List<Snake> snakes);
	/**
	 * Called when the game is over
	 * 
	 * @param winner
	 */
	public void gameOver(Snake winner);
	
	/**
	 * Called every second, represents game trick
	 * 
	 * @param currentTime
	 */
	public void gameTimeTick(int currentTime);
}
