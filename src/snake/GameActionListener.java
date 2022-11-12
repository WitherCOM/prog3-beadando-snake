package snake;

public interface GameActionListener {
	public void updated();
	public void snakeEatenFood(Snake s);
	public void gameOver();
}
