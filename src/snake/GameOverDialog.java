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
	
	public void initComponents()
	{
		setLayout(new BorderLayout());
		add(new JLabel(msg + " is the winner!"),BorderLayout.NORTH);
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
