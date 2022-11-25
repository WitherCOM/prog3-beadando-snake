package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import snake.GameData.GameMode;

public class GameFrame extends JFrame {
	
	private Vector<GameData.GameMode> gameModes;
	
	private GameView gameView;
	private NewGameDialog newGameDialog;
	private FileDialog loadGameDialog;
	private FileDialog saveGameDialog;
	private GameOverDialog gameOverDialog;
	private JLabel gameTimeLabel;
	private JLabel pointLabel;
	
	/**
	 * Initializes the game with the given model
	 * This function resizes the window, put a point and gameTime label to the menu bar
	 * Sets the model for the gameView and starts the game 
	 * 
	 * @param model
	 */
	private void initGame(GameData model)
	{
		GameActionAdapter gameActionAdapter = new GameActionAdapter(gameTimeLabel,pointLabel,gameOverDialog);
		model.addGameActionListener(gameActionAdapter);
		
		gameView.setModel(model);
		gameView.getModel().startGame();
		setPreferredSize(gameView.getFrameDimension());
		
		//Init info bar
		StringBuilder pointText = new StringBuilder();
		for(Snake snake : model.getSnakes() )
		{
			pointText.append(snake);
			pointText.append(": ");
			pointText.append(snake.getScore());
			pointText.append(" ");
		}
		pointLabel.setText(pointText.toString());
		gameTimeLabel.setText(String.valueOf(model.getGameMode().gameLength()));
		pack();
	}
	
	/**
	 * Sets the menu items for newGame and for loading and saving the game
	 * 
	 * @param gameMenu
	 */
	public void setGameMenuItems(JMenu gameMenu)
	{
		JMenuItem newGame = new JMenuItem("New game");
		NewGameAction newGameAction = new NewGameAction(newGameDialog, this::initGame);
		newGame.addActionListener(newGameAction);
		gameMenu.add(newGame);
		
		JMenuItem loadGame = new JMenuItem("Load game");
		LoadGameAction loadGameAction = new LoadGameAction(loadGameDialog, this::initGame);
		loadGame.addActionListener(loadGameAction);
		gameMenu.add(loadGame);
		
		JMenuItem saveGame = new JMenuItem("Save game");
		SaveGameAction saveGameAction = new SaveGameAction(saveGameDialog, gameView);
		saveGame.addActionListener(saveGameAction);
		gameMenu.add(saveGame);
	}
	
	
	/**
	 * Initializes the view components for the JFrame
	 */
	public void initViewComponents()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(500, 500));
		
		//North bar
		JPanel northBar = new JPanel();
		northBar.setLayout(new BorderLayout());
		add(northBar,BorderLayout.NORTH);
		
		//Menu Bar
		JMenuBar mb = new JMenuBar();
		northBar.add(mb,BorderLayout.NORTH);
		
		//File dialogs
		loadGameDialog = new FileDialog(this,"Load game!",FileDialog.LOAD);
		loadGameDialog.setFile("*.snake");
		saveGameDialog = new FileDialog(this,"Save game!",FileDialog.SAVE);
		saveGameDialog.setFile("*.snake");
		//Create newGameDialog
		newGameDialog = new NewGameDialog(this, gameModes);
		
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
		
		try {
			File gameModesXML = new File("./gamemodes.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(gameModesXML);
			doc.getDocumentElement().normalize();	
			NodeList gamemodes = doc.getDocumentElement().getChildNodes();
			gameModes = new Vector<GameMode>();
			for(int i = 0; i < gamemodes.getLength();++i)
			{
				Node node = gamemodes.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element gamemode = (Element)node;
					GameMode gm = new GameMode(
							gamemode.getElementsByTagName("name").item(0).getTextContent(), 
							Integer.parseInt(gamemode.getElementsByTagName("snake-move-interval").item(0).getTextContent()), 
							Integer.parseInt(gamemode.getElementsByTagName("food-spawn-interval").item(0).getTextContent()), 
							Integer.parseInt(gamemode.getElementsByTagName("game-length").item(0).getTextContent()),
							Integer.parseInt(gamemode.getElementsByTagName("map-size").item(0).getTextContent())
					);
					gameModes.add(gm);
				}
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	class GameActionAdapter implements GameActionListener {
		
		JLabel gameTimeLabel;
		JLabel pointLabel;
		GameOverDialog gameOverDialog;
		
		public GameActionAdapter(JLabel gameTimeLabel,JLabel pointLabel,GameOverDialog gameOverDialog)
		{
			this.gameOverDialog = gameOverDialog;
			this.pointLabel = pointLabel;
			this.gameTimeLabel = gameTimeLabel;
		}
		
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
	}
	
	interface InitGameFunc {
		public void initGame(GameData gameData);
	}
	
	public class LoadGameAction implements ActionListener {
		
		private FileDialog loadGameDialog;
		private InitGameFunc initGameFunc;
		public LoadGameAction(FileDialog loadGameDialog,InitGameFunc initGameFunc)
		{
			this.loadGameDialog = loadGameDialog;
			this.initGameFunc = initGameFunc;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			loadGameDialog.setVisible(true);
			try {
				ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(loadGameDialog.getFile()));
				GameData gameData = (GameData)inputStream.readObject();
				if(gameData != null)
					initGameFunc.initGame(gameData);
				inputStream.close();
			} catch (IOException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			initGameFunc.initGame(null);
		}
	}
	
	public class SaveGameAction implements ActionListener {
		
		private FileDialog saveGameDialog;
		private GameView gameView;
		public SaveGameAction(FileDialog saveGameDialog,GameView gameView)
		{
			this.saveGameDialog = saveGameDialog;
			this.gameView = gameView;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveGameDialog.setVisible(true);
			try {
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(saveGameDialog.getFile()));
				outputStream.writeObject(gameView.getModel());
				outputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public class NewGameAction implements ActionListener
	{
		private NewGameDialog newGameDialog;
		private InitGameFunc initGameFunc;
		public NewGameAction(NewGameDialog newGameDialog,InitGameFunc initGameFunc)
		{
			this.newGameDialog = newGameDialog;
			this.initGameFunc = initGameFunc;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			newGameDialog.setVisible(true);
			if(newGameDialog.getGameData() != null)
			{
				initGameFunc.initGame(newGameDialog.getGameData());
			}
		}
	}
	
}
