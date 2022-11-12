package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class GameFrame extends JFrame {
	
	private Vector<GameData.GameMode> gameModes;
	
	private GameView gameView;
	private NewGameDialog newGameDialog;
	
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
		setMinimumSize(new Dimension(500, 500));
		//Menu Bar
		JMenuBar mb = new JMenuBar();
		add(mb,BorderLayout.NORTH);
		
		
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
				NewGameDialog temp = (NewGameDialog)e.getComponent();
				System.out.print(temp.getComboBoxModel().getSelectedItem());
			}
		});
		
		//GameMenu
		JMenu gameMenu = new JMenu("Game");
		mb.add(gameMenu);
		setGameMenuItems(gameMenu);
		
		//Game view
		gameView = new GameView();
		add(gameView,BorderLayout.CENTER); 
		
		pack();
	}
	
	public GameFrame()
	{
		super("Multiplayer Snake");
				
		gameModes = new Vector<GameData.GameMode>();
		gameModes.add(new GameData.GameMode("EASY",1000, 10000, 10, 40));
		gameModes.add(new GameData.GameMode("MEDIUM",500, 6000, 10, 20));
		gameModes.add(new GameData.GameMode("HARD",500, 3000, 10, 10));
		
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
