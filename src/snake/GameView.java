package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public class GameView extends JPanel {
	final int WIDTH = 20;
	final int HEIGHT = 20;
	
	private GameData model;

	public GameData getModel() {
		return model;
	}

	public void setModel(GameData model) {
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
		getActionMap().clear();
		this.model = model;
		this.model.getSnakes().forEach(t -> setInputLayout(t));
		this.model.addGameActionListener(new GameActionListener() {
			
			@Override
			public void updated() {
				// TODO Auto-generated method stub
				repaint();
			}
			
			@Override
			public void snakeEatenFood(Snake s) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void gameOver() {
				// TODO Auto-generated method stub
				System.out.println("Game over!");
			}
		});
		
		setPreferredSize(new Dimension(model.getGameMode().mapSize()*WIDTH,model.getGameMode().mapSize()*HEIGHT));
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D drawer = (Graphics2D)g;
		if(model != null)
		{
		for(Food food : model.getFoods() )
		{
			drawer.setColor(Color.RED);
			drawer.fillRect(food.getX()*WIDTH, food.getY()*HEIGHT, WIDTH, HEIGHT);
		}
		for(Snake snake : model.getSnakes() )
		{
				for(Point p : snake.getBodyPoints())
				{
					drawer.setColor(Color.GREEN);
					drawer.fillRect(p.x*WIDTH, p.y*HEIGHT, WIDTH, HEIGHT);
					drawer.setColor(Color.BLACK);
					drawer.drawRect(p.x*WIDTH, p.y*HEIGHT, WIDTH, HEIGHT);
				}
				drawer.setColor(Color.BLUE);
				drawer.fillRect(snake.getHeadX()*WIDTH, snake.getHeadY()*HEIGHT, WIDTH, HEIGHT);
				drawer.setColor(Color.BLACK);
				drawer.drawRect(snake.getHeadX()*WIDTH, snake.getHeadY()*HEIGHT, WIDTH, HEIGHT);
		}
		
	}
	}
	
    private void addKeyAction(int keyCode, String Name, Action action){
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), Name);
        getActionMap().put(Name, action);
    }
    
    private void setInputLayout(Snake snake)
    {
		addKeyAction(snake.getControl().upKeyCode(), snake +"_UP", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				snake.turnUp();
			}
		});
		addKeyAction(snake.getControl().rightKeyCode(), snake +"_RIGHT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				snake.turnRight();
			}
		});
		addKeyAction(snake.getControl().downKeyCode(), snake +"_DOWN", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				snake.turnDown();
			}
		});
		addKeyAction(snake.getControl().leftKeyCode(), snake +"_LEFT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				snake.turnLeft();
			}
		});
    }
}
