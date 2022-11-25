package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverDialog extends JDialog
{
	private String msg;
	private JLabel label;
	
	/**
	 *  Initialize components for game over dialog
	 *  
	 */
	public void initComponents()
	{
		setLayout(new BorderLayout());
		label = new JLabel();
		add(label,BorderLayout.NORTH);
		JButton okBtn = new JButton("Ok");
		setMinimumSize(new Dimension(300,150));
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		add(okBtn,BorderLayout.SOUTH);
		pack();
	}
	
	public GameOverDialog(JFrame frame)
	{
		super(frame,"Game over!");
		initComponents();
	}
	
	/**
	 * Getter function for message
	 * 
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the message of the game over dialog
	 * 
	 * @param msg The message to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
		label.setText(msg + " is the winner!");
	}
}
