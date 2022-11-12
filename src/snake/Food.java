package snake;

import java.awt.Point;
import java.io.Serializable;

public class Food implements Serializable {
	private Point location;
	
	public Food(int x, int y) {
		location = new Point(x,y);
	}
	
	public int getX()
	{
		return location.x;
	}
	
	public int getY()
	{
		return location.y;
	}

}
