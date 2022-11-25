package snake;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;



public class Snake implements Serializable, Comparable<Snake> {	
	private static int autoIncrement = 0;
	private int id;
	private boolean canTurn = true;
	private Point head;
	private int mapSize;
	private int score;
	private Direction direction;
	private ArrayList<Point> body;
	private InputLayout control;
	
	public Snake(int x,int y,int length, int mapSize, Direction direction, InputLayout control)
	{
		id = ++autoIncrement;
		head = new Point(x,y);
		this.mapSize = mapSize;
		this.direction = direction;
		this.control = control;
		
		body = new ArrayList<Point>();
		body.add(neighbourPointByReversedDirection(head, direction));
		for(int i = 1; i < length;++i)
		{
			body.add(neighbourPointByReversedDirection(body.get(i-1), direction));
		}
	}
	
	/**
	 * Turns UP the snake if it not faces DOWN and can turn.
	 */
	public void turnUp()
	{
		if(direction != Direction.DOWN && canTurn)
			direction = Direction.UP;
	}
	
	/**
	 * Turns RIGHT the snake if it not faces LEFT and can turn.
	 */
	public void turnRight()
	{
		if(direction != Direction.LEFT && canTurn)
			direction = Direction.RIGHT;
	}
	
	/**
	 * Turns DOWN the snake if it not faces UP and can turn.
	 */
	public void turnDown()
	{
		if(direction != Direction.UP && canTurn)
			direction = Direction.DOWN;
	}
	
	/**
	 * Turns LEFT the snake if it not faces RIGHT and can turn.
	 */
	public void turnLeft()
	{
		if(direction != Direction.RIGHT && canTurn)
			direction = Direction.LEFT;
	}
	
	/**
	 * Increases the snake length with adding a new point to the body points list
	 */
	synchronized public void increaseLength()
	{
		body.add(new Point(head));
	}
	
	/**
	 * Moves the snake. Changes the head position with the chosen direction. Put the previous 
	 * head point into the start of the body array and removes the last point from the 
	 * body array.
	 */
	synchronized public void move()
	{
		body.add(0,new Point(head));
		body.remove(body.size()-1);
		head = neighbourPointByDirection(head, direction);
		canTurn = true;
	}
	
	/**
	 * Return the array of the body points
	 * 
	 * @return
	 */
	public ArrayList<Point> getBodyPoints() {
		return body;
	}
	
	/**
	 * Calculates the neighbor point of a point with a given direction
	 * 
	 * @param p
	 * @param dir
	 * @return
	 */
	private Point neighbourPointByDirection(Point p, Direction dir)
	{
		switch(dir)
		{
		case DOWN:
			return new Point(p.x,(p.y+1)%mapSize);
		case LEFT:
			return new Point((p.x-1+mapSize)%mapSize,p.y);
		case RIGHT:
			return new Point((p.x+1)%mapSize,p.y);
		case UP:
			return new Point(p.x,(p.y-1+mapSize)%mapSize);	
		}
		return new Point(p);
	}
	
	
	/**
	 * Calculates the opposite neighbor point of a point with a given direction
	 * 
	 * @param p
	 * @param dir
	 * @return
	 */
	private Point neighbourPointByReversedDirection(Point p, Direction dir)
	{
		switch(dir)
		{
		case UP:
			return new Point(p.x,(p.y+1)%mapSize);
		case RIGHT:
			return new Point((p.x-1+mapSize)%mapSize,p.y);
		case LEFT:
			return new Point((p.x+1)%mapSize,p.y);
		case DOWN:
			return new Point(p.x,(p.y-1+mapSize)%mapSize);	
		}
		return new Point(p);
	}
	
	public int getHeadX()
	{
		return head.x;
	}
	
	public int getHeadY()
	{
		return head.y;
	}
	
	/**
	 * Checks if the snakes hit a food or not
	 * 
	 * @param food
	 * @return
	 */
	public boolean hitFood(Food food)
	{
		if(head.x == food.getX() && head.y == food.getY())
		{
			score++;
			increaseLength();
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a snakes hit another snake or itself
	 * 
	 * @param snake
	 * @return
	 */
	public boolean hitSnake(Snake snake)
	{
		for(Point p : snake.getBodyPoints())
		{
			if(p.equals(head))
				return true;
		}
		if(this != snake)
			return snake.getHeadX() == head.x && snake.getHeadY() == head.y;
		else
			return false;
	}
	
	public String toString()
	{
		return "S"+id;
	}

	public InputLayout getControl() {
		return control;
	}
	
	public int getScore() {
		return score;
	}
	
	public record InputLayout(int upKeyCode,int rightKeyCode,int downKeyCode,int leftKeyCode) implements Serializable {}
	
	//Enum direction
	public enum Direction {
		UP,DOWN,RIGHT,LEFT
	}

	@Override
	public int compareTo(Snake o) {
		// TODO Auto-generated method stub
		if(score > o.score)
			return 1;
		else if(score == o.score)
			return 0;
		else
			return -1;
	}

}
