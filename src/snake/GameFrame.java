package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class GameFrame extends JFrame {
	
	private Vector<GameData.GameMode> gameModes;
	
	private GameView gameView;
	private NewGameDialog newGameDialog;
	private GameOverDialog gameOverDialog;
	private JLabel gameTimeLabel;
	private JLabel pointLabel;
	
	public void setGameMenuItems(JMenu gameMenu)
	{
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				newGameDialog.setVisible(true);
			}
		});
		gameMenu.add(newGame);
		JMenuItem loadGame = new JMenuItem("Load game");
		gameMenu.add(loadGame);
		JMenuItem saveGame = new JMenuItem("Save game");
		gameMenu.add(saveGame);
	}
	
	public void initViewComponents()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setPreferredSize(new Dimension(500, 500));
		
		//North bar
		JPanel northBar = new JPanel();
		northBar.setLayout(new BorderLayout());
		add(northBar,BorderLayout.NORTH);
		
		//Menu Bar
		JMenuBar mb = new JMenuBar();
		northBar.add(mb,BorderLayout.NORTH);
		
		
		//Create newGameDialog
		newGameDialog = new NewGameDialog(this, gameModes);
		newGameDialog.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				NewGameDialog newDialog = (NewGameDialog)e.getComponent();
				if(newDialog.getGameData() != null)
				{
					newDialog.getGameData().addGameActionListener(new GameActionListener() {
						
						@Override
						public void updated() {
							// TODO Auto-generated method stub

						}
						
						@Override
						public void snakeEatenFood(List<Snake> snakes) {
							StringBuilder pointText = new StringBuilder();
							for(Snake snake : snakes )
							{
								pointText.append(snake);
								pointText.append(": ");
								pointText.append(snake.getScore());
								pointText.append(" ");
							}
							pointLabel.setText(pointText.toString());
						}
						
						@Override
						public void gameTimeTick(int currentTime) {
							// TODO Auto-generated method stub
							gameTimeLabel.setText(String.valueOf(currentTime));
						}
						
						@Override
						public void gameOver(Snake winner) {
							// TODO Auto-generated method stub
							gameOverDialog.setMsg(winner.toString());
							gameOverDialog.setVisible(true);
						}
					});
					
					gameView.setModel(newDialog.getGameData());
					gameView.getModel().startGame();
					setPreferredSize(gameView.getFrameDimension());
					
					//Init info bar
					StringBuilder pointText = new StringBuilder();
					for(Snake snake : newDialog.getGameData().getSnakes() )
					{
						pointText.append(snake);
						pointText.append(": ");
						pointText.append(snake.getScore());
						pointText.append(" ");
					}
					pointLabel.setText(pointText.toString());
					gameTimeLabel.setText(String.valueOf(newDialog.getGameData().getGameMode().gameLength()));
					pack();
				}
			}
		});
		
		//Game over dialog
		gameOverDialog = new GameOverDialog(this);
		
		//GameMenu
		JMenu gameMenu = new JMenu("Game");
		mb.add(gameMenu);
		setGameMenuItems(gameMenu);
		
		//Info panel
		JPanel infoPanel = new JPanel();
		infoPanel.add(new JLabel("Game time: "));
		gameTimeLabel = new JLabel();
		infoPanel.add(gameTimeLabel);
		northBar.add(infoPanel,BorderLayout.WEST);
		infoPanel.add(new JLabel("Points: "));
		pointLabel = new JLabel();
		infoPanel.add(pointLabel);
		
		//Game view
		gameView = new GameView();
		add(gameView,BorderLayout.CENTER); 
		
		pack();
	}
	
	public GameFrame()
	{
		super("Multiplayer Snake");
				
		gameModes = new Vector<GameData.GameMode>();
		gameModes.add(new GameData.GameMode("EASY",1000, 10000, 100, 40));
		gameModes.add(new GameData.GameMode("MEDIUM",500, 6000, 100, 20));
		gameModes.add(new GameData.GameMode("HARD",500, 3000, 100, 20));
		
		initViewComponents();
	}
	
	public static void main(String[] args)
	{
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

        		GameFrame frame = new GameFrame();
        		frame.setVisible(true);
            }
        });
	}
	
	
}
