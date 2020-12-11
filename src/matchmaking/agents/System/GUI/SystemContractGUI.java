package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import matchmaking.agents.System.SystemAgent;
import matchmaking.orm.DataBase;
import matchmaking.orm.User;

public class SystemContractGUI extends JFrame {
	

	public SystemContractGUI(User user) {

		
		

		// Creating the Frame
		JFrame frame = new JFrame("System Contract");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(900, 100);

		

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton agree = new JButton("Agree");
		JButton disagree = new JButton("Disagree");
		panel.add(agree);
		panel.add(disagree);
		
		
		agree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				 frame.dispose();
			}

		});

		disagree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 frame.dispose();
				 // if the provider does not agree with the contract terms, he/she has to be downgraded to Client user Type
				 user.downgradeToClient();
			}

		});
		

		JPanel p = new JPanel();
		String labelcontent = "<html>system will get 20 percent on each project.do you agree <br/>"
				+ "(please pay note that if you disagree system will convert your account to a client)";
		JLabel content = new JLabel(labelcontent,SwingConstants.CENTER);
		
		p.add(content);
		
		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
	
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}

