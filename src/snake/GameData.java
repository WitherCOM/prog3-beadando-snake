package snake;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

public class GameData implements Serializable {
	private List<Snake> snakes;
	private List<Food> foods;
	private GameMode gameMode;
	private List<GameActionListener> gameActionListeners;
	private Timer foodSpawner;
	private Timer snakeMover;
	private Timer gameTimer;
	private int currentTime;

	public GameData() {
		foods = Collections.synchronizedList(new ArrayList<Food>());
		gameActionListeners = new ArrayList<GameActionListener>();
		snakes = new ArrayList<Snake>();
	}

	public void addSnake(Snake snake) {
		snakes.add(snake);
	}

	public List<Snake> getSnakes() {
		return snakes;
	}

	public List<Food> getFoods() {
		return foods;
	}
	
	public void addFood(Food food) {
		foods.add(food);
	}
	
	public void removeFood(Food food)
	{
		foods.remove(food);
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
		currentTime = gameMode.gameLength();
		setupTimers();
	}

	public void addGameActionListener(GameActionListener gameActionListener) {
		gameActionListeners.add(gameActionListener);
	}

	public void startGame() {
		foodSpawner.start();
		snakeMover.start();
		gameTimer.start();
	}

	public void stopGame() {
		foodSpawner.stop();
		snakeMover.stop();
		gameTimer.stop();
	}

	private void setupTimers() {
		foodSpawner = new Timer(gameMode.foodSpawnInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				addFood(new Food(rand.nextInt(gameMode.mapSize()), rand.nextInt(gameMode.mapSize())));
				gameActionListeners.forEach(t -> t.updated());
			}
		});
		snakeMover = new Timer(gameMode.snakeMoveInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Snake snake : snakes) {
					for (Food food : foods) {
						if (snake.hitFood(food)) {
							removeFood(food);
							gameActionListeners.forEach(t -> {
								t.updated();
								t.snakeEatenFood(snakes);
							});
							return;
						}
					}

				}
				snakes.forEach(t -> t.move());
				for (Snake snake1 : snakes) {
					for (Snake snake2 : snakes) {
						if (snake1.hitSnake(snake2)) {
							stopGame();
							gameActionListeners.forEach(t -> t.gameOver(snake2));
							return;
						}
					}
				}
				gameActionListeners.forEach(t -> t.updated());
			}
		});
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentTime == 0)
				{
					stopGame();
					gameActionListeners.forEach(t -> t.gameOver(Collections.max(snakes)));
					return;
				}
				currentTime--;
				gameActionListeners.forEach(t -> t.gameTimeTick(currentTime));
			}
		});
	}

	public record GameMode(String name, int snakeMoveInterval, int foodSpawnInterval, int gameLength, int mapSize) implements Serializable {};
}
