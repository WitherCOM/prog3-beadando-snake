package snake;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import snake.GameData.GameMode;

public class NewGameDialog extends JDialog {
	
	private GameModeComboBoxModel gameModeComboBoxModel;
	private GameData gameData;
	private GameModeDataView gameModeDataView;
	
	/**
	 * Initializes the components for the new game modal
	 */
	public void initComponents()
	{
		setLayout(new GridLayout(3,1,0,10));
		
		JPanel panel1 = new JPanel();
		JComboBox<GameData.GameMode> gameModeSelection = new JComboBox<GameData.GameMode>();
		gameModeSelection.setModel(gameModeComboBoxModel);
		gameModeSelection.setRenderer(new GameModeListCellRenderer());
		panel1.add(new JLabel("Select game"));
		panel1.add(gameModeSelection);
		add(panel1);
		
		//Game mode data list
		gameModeDataView = new GameModeDataView();
		gameModeDataView.setGameMode((GameData.GameMode)gameModeSelection.getSelectedItem());
		add(gameModeDataView);
		gameModeComboBoxModel.addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				gameModeDataView.setGameMode((GameData.GameMode)gameModeSelection.getSelectedItem());
			}
		});
		
		JPanel panel2 = new JPanel();
		JButton oneBtn = new JButton("One Player");
		oneBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newOnePlayerGame();
			}
		});
		JButton twoBtn = new JButton("Two Player");
		twoBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newTwoPlayerGame();
			}
		});
		panel2.add(oneBtn);
		panel2.add(twoBtn);
		add(panel2);
		pack();
	}
	
	public NewGameDialog(JFrame frame,List<GameData.GameMode> gameModes)
	{
		super(frame,"New game",true);
		gameModeComboBoxModel = new GameModeComboBoxModel(gameModes);
		initComponents();
	}
	
	/**
	 * Creates a new one player game with W,A,S,D control
	 */
	private void newOnePlayerGame()
	{
		GameData.GameMode gameMode = (GameData.GameMode)gameModeComboBoxModel.getSelectedItem();
		gameData = new GameData();
		gameData.setGameMode(gameMode);
		Snake.InputLayout wasdInput = new Snake.InputLayout(KeyEvent.VK_W, KeyEvent.VK_D,KeyEvent.VK_S,KeyEvent.VK_A);
		gameData.addSnake(new Snake(5, 2, 4, gameMode.mapSize(), Snake.Direction.RIGHT,wasdInput));
		setVisible(false);
	}
	
	/**
	 * Creates a new two player game with arrow and W,A,S,D control
	 */
	private void newTwoPlayerGame()
	{
		GameData.GameMode gameMode = (GameData.GameMode)gameModeComboBoxModel.getSelectedItem();
		gameData = new GameData();
		gameData.setGameMode(gameMode);
		Snake.InputLayout wasdInput = new Snake.InputLayout(KeyEvent.VK_W, KeyEvent.VK_D,KeyEvent.VK_S,KeyEvent.VK_A);
		Snake.InputLayout arrowInput = new Snake.InputLayout(KeyEvent.VK_UP, KeyEvent.VK_RIGHT,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT);
		gameData.addSnake(new Snake(5, 2, 4, gameMode.mapSize(), Snake.Direction.RIGHT,wasdInput));
		gameData.addSnake(new Snake(5, 4, 4, gameMode.mapSize(), Snake.Direction.RIGHT,arrowInput));
		setVisible(false);
	}
	
	public GameData getGameData() {
		return gameData;
	}
	
	public class GameModeListCellRenderer extends DefaultListCellRenderer {

	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	        if (value instanceof GameMode) {
	            value = ((GameMode)value).name();
	        }

	        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 

	    }

	}
}
