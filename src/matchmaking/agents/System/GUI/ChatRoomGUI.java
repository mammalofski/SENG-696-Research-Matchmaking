
package matchmaking.agents.System.GUI;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ChatRoomGUI extends JFrame {

	JTextField messageTxt;
	SystemAgent myAgent;
	User user;
	private int chatRoomId;
	private String messageText;
	private MessageTemplate template;
	DefaultTableModel tableModel;

	public ChatRoomGUI(SystemAgent agent, User user1, int chatRoomId1) {
		myAgent = agent;
		user = user1;
		chatRoomId = chatRoomId1;
		
		// TODO: make a request and fetch the chat room and its messages

		JFrame frame = new JFrame("ChatRoomGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);

		JPanel messagesPanel = new JPanel(); // the panel is not visible in output
		
		ArrayList<Message> messages = getMessages();
		String data[][] = convertMessagesToData(messages);

//		String data[][] = { {"1", "2", "some random message body", "some random date"} };

		String column[] = {"Sender", "Body", "Date"};
//		JTable jt = new JTable(data, column);
		tableModel = new DefaultTableModel(data, column); 
		JTable jt = new JTable(tableModel); 
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
				Message message = sendMessage();
				System.out.println("the message in chat room gui is " + message.getBody());
				addMessageToDialogue(message);

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
	
	private ArrayList<Message> getMessages() {
		try {
			ACLMessage msg, reply;
			System.out.println("getting the messages");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("chatRoomId", Integer.toString(chatRoomId));
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.GET_MESSAGES);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
			
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.GET_MESSAGES));
			msg = myAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<Message> messages = (ArrayList<Message>) msg.getContentObject();
				return messages;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Message sendMessage() {
		try {
			ACLMessage msg, reply;
			System.out.println("sending message");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("userId", Integer.toString(user.getId()));
			requestBody.put("chatRoomId", Integer.toString(chatRoomId));
			requestBody.put("messageBody", messageTxt.getText().trim());
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.SEND_MESSAGE);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
			
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.SEND_MESSAGE));
			msg = myAgent.blockingReceive(template);
			if (msg != null) { //
				Message message = (Message) msg.getContentObject();
				return message;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void addMessageToDialogue(Message message) {
		tableModel.addRow(new Object[] {user.getName(), message.getBody(), message.getDate()});
	}
	
	private String[][] convertMessagesToData(ArrayList<Message> messages) {
		int lenMessages = messages.size();
		String[][] data = new String[lenMessages][3];
		Message tmpMessage;
		for (int i = 0; i < lenMessages; i++) {
			tmpMessage = messages.get(i);
			String rowData[] = {tmpMessage.getSenderName(), tmpMessage.getBody(), tmpMessage.getDate()};
			for (int j = 0; j < 3; j++) {
				data[i][j] = rowData[j];
			}
		}
		return data;
	}

}
