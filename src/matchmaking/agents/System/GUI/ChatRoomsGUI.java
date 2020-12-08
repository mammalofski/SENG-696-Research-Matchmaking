
package matchmaking.agents.System.GUI;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
//import matchmaking.agents.systemAgent.systemAgentAgent;
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
import javax.swing.table.TableCellRenderer;

public class ChatRoomsGUI extends JFrame {
	private ACLMessage msg, reply;
	private MessageTemplate template;
	JFrame frame1;
	private User user;
	private SystemAgent systemAgent;
	public ChatRoomsGUI (SystemAgent agent,User currentUser)
	{
		user=currentUser;
		systemAgent=agent;
		
		frame1 = new JFrame("Chat Rooms");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(400, 400);
		
		frame1.setVisible(true);

		ArrayList<ChatRoom> chatrooms = getChatrooms(user.getId());
		showInGUI(chatrooms);
	}
private ArrayList<ChatRoom> getChatrooms(int userId) {
		
		try {
			System.out.println("getting the chat rooms");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("userId", Integer.toString(userId));
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.GET_CHATROOMS);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.GET_CHATROOMS));
			msg = systemAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<ChatRoom> chatRooms = (ArrayList<ChatRoom>) msg.getContentObject();
				return chatRooms;
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
	

private void showInGUI(ArrayList<ChatRoom> chatrooms) {
		
		int lenChatRooms = chatrooms.size();
		System.out.println("lenChatRooms " + lenChatRooms);
		if (lenChatRooms == 0)
			return;
		String column[] = { "Index", "projectName" };
		String[][] data = new String[lenChatRooms][2];
		ChatRoom tempChat;
		for (int i = 0; i < lenChatRooms; i++) {
			tempChat = chatrooms.get(i);
			String rowData[] = {Integer.toString(tempChat.getId()), tempChat.getProjectName()};	
			System.out.println(rowData);
			for (int j = 0; j < 2; j++) {
				data[i][j] = rowData[j];
			}
		}
		
		JTable table = new JTable(new ChatRoomsJTable(data, systemAgent, user));
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("ShowChats").setCellRenderer(buttonRenderer);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		//JPanel panel = new JPanel();
		//panel.add(table);
		// Adding Components to the frame.
		frame1.getContentPane().add(BorderLayout.CENTER, table);
	}
	public  void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

}
