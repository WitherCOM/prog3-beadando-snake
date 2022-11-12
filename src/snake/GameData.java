package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

public class GameData {
	private List<Snake> snakes;
	private List<Food> foods;
	private GameMode gameMode;
	private List<GameActionListener> gameActionListeners;
	private Timer foodSpawner;
	private Timer snakeMover;

	
	public GameData() 
	{
		foods = Collections.synchronizedList(new ArrayList<Food>());
		gameActionListeners = new ArrayList<GameActionListener>();
		snakes = new ArrayList<Snake>();
	}
	
	public void addSnake(Snake snake)
	{
		snakes.add(snake);
	}
	
	public List<Snake> getSnakes()
	{
		return snakes;
	}
	
	public List<Food> getFoods()
	{
		return foods;
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
		setupTimers();
	}
	
	public void addGameActionListener(GameActionListener gameActionListener)
	{
		gameActionListeners.add(gameActionListener);
	}
	
	public void startGame()
	{
		foodSpawner.start();
		snakeMover.start();
	}
	
	public void stopGame()
	{
		foodSpawner.stop();
		snakeMover.stop();
	}
	
	private void setupTimers()
	{
		foodSpawner = new Timer(gameMode.foodSpawnInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				foods.add(new Food(rand.nextInt(gameMode.mapSize()),rand.nextInt(gameMode.mapSize())));
				gameActionListeners.forEach(t -> t.updated());
			}
		}); 
		snakeMover = new Timer(gameMode.snakeMoveInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Snake snake1 : snakes)
				{
					for(Food food : foods)
						{
							if(snake1.hitFood(food))
							{
								snake1.setScore(snake1.getScore()+1);
								foods.remove(food);
								gameActionListeners.forEach(t -> {t.updated();t.snakeEatenFood(snake1);});
								return;
							}
						}

				}
				snakes.forEach(t -> t.move());
				for(Snake snake1 : snakes)
				{
					for(Snake snake2 : snakes)
					{
						if(snake1.hitSnake(snake2))
						{
							stopGame();
							gameActionListeners.forEach(t -> t.gameOver());
							return;
						}
					}
				}
				gameActionListeners.forEach(t -> t.updated());
			}
		}); 
	}

	public record GameMode(String name,int snakeMoveInterval, int foodSpawnInterval,int gameLengthInterval, int mapSize) implements Serializable {
		public String toString() {return name;}
	};
}
