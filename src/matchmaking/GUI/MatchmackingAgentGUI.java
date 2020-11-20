package matchmaking.agents;

import jade.core.AID;
import orm.User;
import matchmaking.agents.UserViewModel;
import matchmaking.orm.DataBase;

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

class MatchmackingAgentGUI extends JFrame {
	private MatchmakerAgent myAgent;

	private JTextField titleField, priceField;
	CardLayout card;
	JButton b1, b2, b3;
	Container c;

	MatchmackingAgentGUI(MatchmakerAgent matchmackerAgent) {

		super(matchmackerAgent.getLocalName());

		myAgent = matchmackerAgent;
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
					try {
						Statement statement = conn.createStatement();

						statement.setQueryTimeout(30);

						statement.executeUpdate("drop table if exists person");
						statement.executeUpdate("create table person (id integer, name string)");
						statement.executeUpdate("insert into person values(1, 'leo')");
						statement.executeUpdate("insert into person values(2, 'yui')");

						ResultSet rs = statement.executeQuery("select * from person");
						while (rs.next()) {
							System.out.println("name = " + rs.getString("name"));
							System.out.println("id = " + rs.getInt("id"));
						}
					} catch (SQLException e) {
						System.out.println("in catch");
						System.out.println(e);
						System.err.println(e.getMessage());
					} finally {
						try {
							if (conn != null)
								conn.close();
						} catch (SQLException e) {
							System.err.println(e.getMessage());
						}
					}

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
		UserViewModel user = new UserViewModel();
		Class user_cls = user.getClass();
		Field[] fields = user_cls.getDeclaredFields();

		System.out.printf("%d fields:%n", fields.length);
		for (Field field : fields) {
			if (field.getName() != "id") {
				var name = field.getName();
				var type = field.getType();
				p.add(new JLabel(name));
				p.add(new JTextField(20));

			}
		}

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, mb);
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
