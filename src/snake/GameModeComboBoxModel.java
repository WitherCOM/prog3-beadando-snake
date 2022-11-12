package snake;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import snake.GameData.GameMode;

public class GameModeComboBoxModel implements ComboBoxModel<GameData.GameMode> {

	private List<GameData.GameMode> gameModes;
	private GameData.GameMode selected;
	
	public GameModeComboBoxModel(List<GameData.GameMode> gameModes)
	{
		this.gameModes = gameModes;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return gameModes.size();
	}

	@Override
	public GameMode getElementAt(int index) {
		// TODO Auto-generated method stub
		return gameModes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO Auto-generated method stub
		selected = (GameData.GameMode)anItem;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selected;
	}

}
