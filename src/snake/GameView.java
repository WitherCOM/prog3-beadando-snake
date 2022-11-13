package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public class GameView extends JPanel {
	final int WIDTH = 20;
	final int HEIGHT = 20;
	
	private JLabel pauseLabel;
	private GameData model;

	public GameView() {
		setLayout(new BorderLayout());
		pauseLabel = new JLabel("Press R to resume!");
		pauseLabel.setFont(new Font("arial",0,32));
		add(pauseLabel,BorderLayout.CENTER);
		pauseLabel.setVisible(false);
	}
	
	public GameData getModel() {
		return model;
	}

	public void setModel(GameData model) {
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
		getActionMap().clear();
		this.model = model;
		this.model.getSnakes().forEach(t -> setInputLayout(t));
		addKeyAction(KeyEvent.VK_P, "pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopGame();
				pauseLabel.setVisible(true);
			}
		});
		addKeyAction(KeyEvent.VK_R, "resume", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.startGame();
				pauseLabel.setVisible(false);
			}
		});
		this.model.addGameActionListener(new GameActionListener() {
			
			@Override
			public void updated() {
				// TODO Auto-generated method stub
				repaint();
			}
			
			@Override
			public void snakeEatenFood(List<Snake> snakes) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void gameOver(Snake winner) {
				// TODO Auto-generated method stub
				System.out.println("Game over!");
			}

			@Override
			public void gameTimeTick(int currentTime) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D drawer = (Graphics2D)g;
		if(model != null)
		{		
		
		//Draw food(s)
		for(Food food : model.getFoods() )
		{
			drawer.setColor(Color.RED);
			drawer.fillRect(food.getX()*WIDTH, food.getY()*HEIGHT, WIDTH, HEIGHT);
		}
		
		//Draw snake(s)
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
				drawer.setColor(Color.WHITE);
				drawer.drawChars(snake.toString().toCharArray(), 0, 2, snake.getHeadX()*WIDTH+WIDTH/4, snake.getHeadY()*HEIGHT+HEIGHT/2);
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
    
    public Dimension getFrameDimension()
    {
    	return new Dimension(model.getGameMode().mapSize()*WIDTH,model.getGameMode().mapSize()*HEIGHT);
    }
}
