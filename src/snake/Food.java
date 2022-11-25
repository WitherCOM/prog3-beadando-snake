package snake;

import java.awt.Point;
import java.io.Serializable;

public class Food implements Serializable {
	private Point location;
	
	public Food(int x, int y) {
		location = new Point(x,y);
	}
	
	/**
	 * Get x coordinate of food position
	 * 
	 * @return Integer
	 */
	public int getX()
	{
		return location.x;
	}
	
	/**
	 * Get y coordinate of food position
	 * 
	 * @return Integer
	 */
	public int getY()
	{
		return location.y;
	}

}
