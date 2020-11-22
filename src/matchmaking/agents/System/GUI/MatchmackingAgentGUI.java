package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.User;
import matchmaking.orm.DataBase;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class MatchmackingAgentGUI extends JFrame {
	private SystemAgent myAgent;

	private JTextField titleField, priceField;
	CardLayout card;
	JButton b1, b2, b3;
	Container c;
	private   JTextField nameTxt;

	public MatchmackingAgentGUI(SystemAgent systemAgent) {

		super(systemAgent.getLocalName());

		myAgent = systemAgent;
		Connection conn = DataBase.getConnection();

		// Creating the Frame
		JFrame frame = new JFrame("Chat Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		// Creating the MenuBar and adding components
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("Profile");
		JMenu m2 = new JMenu("UserList");
		JMenu m3 = new JMenu("Bids");
		JMenu m4 = new JMenu("projectProgress");
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		JMenuItem m11 = new JMenuItem("show Profile");
		m1.add(m11);
		m11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("menu item has clicked");
				JFrame frame1 = new JFrame("My First GUI");
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setSize(300, 300);
				JButton button1 = new JButton("Button 1");
				JButton button2 = new JButton("Button 2");
				frame1.getContentPane().add(button1);
				frame1.getContentPane().add(button2);
				frame1.setVisible(true);
			}
		});
		;

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton save = new JButton("save");
		JButton cancel = new JButton("cancel");

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {

					System.out.println("show gui has runned");
					saveUser();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(MatchmackingAgentGUI.this, "Invalid values. " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panel.add(save);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(5, 2));
		
		JLabel name=new JLabel("name");
		nameTxt=new JTextField(20);
		p.add(name);
		p.add(nameTxt);
		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	private void saveUser () {
		String name = nameTxt.getText().trim();
		System.out.println("name is :" + name);
		User user = new User(name, 1, "email", "username", "pass", false, 1, 1, "keywords", "logo", "website", "cv");
		user.createUser();
		
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
