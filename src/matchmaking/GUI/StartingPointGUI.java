package matchmaking.GUI;

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

import matchmaking.agents.SystemAgent;
import matchmaking.orm.DataBase;
import matchmaking.orm.User;

public class StartingPointGUI extends JFrame {
	private SystemAgent myAgent;

	public StartingPointGUI(SystemAgent systemAgent) {

		super(systemAgent.getLocalName());

		myAgent = systemAgent;
		

		// Creating the Frame
		JFrame frame = new JFrame("Starting Point");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);

		

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton guest = new JButton("Guest");
		JButton user = new JButton("User");
		panel.add(guest);
		panel.add(user);
		
		
		guest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GuestGUI guestGUI=new GuestGUI();
				guestGUI.showGui();
			}

		});

		user.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			UserGUI userGui=new	UserGUI();
			userGui.showGui();	
			}

		});
		

		JPanel p = new JPanel();
		
		JLabel name=new JLabel("Are you a User or a Guest?");
		p.add(name);
		
		
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

