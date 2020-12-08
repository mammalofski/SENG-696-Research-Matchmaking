
package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.ProjectManager.ProjectManagerAgent;
import matchmaking.agents.System.Profiler;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ChatRoomGUI extends JFrame {

	JTextField messageTxt;
	SystemAgent myAgent;
	User user;
	int chatRoomId;

	public ChatRoomGUI(SystemAgent agent, User user1, int chatRoomId1) {
		myAgent = agent;
		user = user1;
		chatRoomId = chatRoomId1;
		
		// TODO: make a request and fetch the chat room and its messages

		JFrame frame = new JFrame("ChatRoomGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);

		JPanel messagesPanel = new JPanel(); // the panel is not visible in output

		String data[][] = { { "1", "Amit","Amit", "A@gmail.com", "c#" }, { "2", "Jai","Amit", "b@gmail.com", "javascript" },
				{ "3", "Sachin","Amit", "c@gmail.com", "java" } };

		String column[] = { "Index", "Provide","Date" , "client", "Date" };
		JTable jt = new JTable(data, column);
		jt.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
//		//jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		messagesPanel.add(sp);

		JPanel panel = new JPanel();

		JButton cancel = new JButton("cancel");
		JButton send = new JButton("send");

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});

		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("sendmessage");

			}
		});

		panel.add(send);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(2, 2));

		JLabel message = new JLabel("Message");
		messageTxt = new JTextField(20);
		p.add(message);
		p.add(messageTxt);

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, messagesPanel);
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
