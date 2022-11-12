package snake;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameModeDataView extends JPanel {
	
	private JLabel foodSpawnLabel;
	private JLabel moveSpeedLabel;
	private JLabel mapSizeLabel;
	private JLabel gameTimeLabel;

	private void initComponents()
	{
		setLayout(new GridLayout(4,2,0,10));
		add(new JLabel("Food Spawn Interval: "));
		foodSpawnLabel = new JLabel();
		add(foodSpawnLabel);
		
		add(new JLabel("Move Interval: "));
		moveSpeedLabel = new JLabel();
		add(moveSpeedLabel);

		add(new JLabel("Map size: "));
		mapSizeLabel = new JLabel();
		add(mapSizeLabel);

		add(new JLabel("Game time: "));
		gameTimeLabel = new JLabel();
		add(gameTimeLabel);
	}
	
	public GameModeDataView()
	{
		initComponents();
	}

	public void setGameMode(GameData.GameMode gameMode) {
		foodSpawnLabel.setText(String.valueOf(gameMode.foodSpawnInterval()));
		moveSpeedLabel.setText(String.valueOf(gameMode.snakeMoveInterval()));
		mapSizeLabel.setText(String.valueOf(gameMode.mapSize()));
		gameTimeLabel.setText(String.valueOf(gameMode.gameLength()));
	}
}
