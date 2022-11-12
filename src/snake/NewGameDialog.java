package snake;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import snake.GameData.GameMode;

public class NewGameDialog extends JDialog {
	
	private GameModeComboBoxModel gameModeComboBoxModel;
	
	public void initComponents()
	{
		setLayout(new GridLayout(2,1,0,100));
		
		JPanel panel1 = new JPanel();
		JComboBox<GameData.GameMode> gameModeSelection = new JComboBox<GameData.GameMode>();
		gameModeSelection.setModel(gameModeComboBoxModel);
		panel1.add(new JLabel("Select game"));
		panel1.add(gameModeSelection);
		add(panel1);
		
		JPanel panel2 = new JPanel();
		JButton oneBtn = new JButton("One Player");
		oneBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newOnePlayerGame();
	
			}
		});
		JButton twoBtn = new JButton("One Player");
		panel2.add(oneBtn);
		panel2.add(twoBtn);
		add(panel2);
		pack();
	}
	
	public NewGameDialog(JFrame frame,List<GameData.GameMode> gameModes)
	{
		super(frame,"New game");
		gameModeComboBoxModel = new GameModeComboBoxModel(gameModes);
		initComponents();
	}
	
	private void newOnePlayerGame()
	{
		setVisible(false);
	}
	
	public GameModeComboBoxModel getComboBoxModel()
	{
		return gameModeComboBoxModel;
	}
}
