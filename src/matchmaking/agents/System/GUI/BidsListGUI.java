
package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import jade.lang.acl.MessageTemplate;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.agents.System.SystemAgent;
import jade.core.AID;
import jade.core.Agent;
import matchmaking.orm.Bid;
import matchmaking.orm.Constants;
import matchmaking.orm.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class BidsListGUI extends JFrame {
	private ACLMessage msg, reply;
	private User user;
	private SystemAgent systemAgent;
	private MessageTemplate template;
	JFrame frame1;
	
	
	public BidsListGUI(SystemAgent agent, User user1) {

		System.out.println("BidsListGUI");
		systemAgent = agent;
		user = user1;
		frame1 = new JFrame("BidsListGUI");
//		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.setSize(400, 400);
		ArrayList<Bid> biddings = getBiddings(user.getId());
		System.out.println("received the biddings: " + biddings);
		showInGUI(biddings);

	}

	private ArrayList<Bid> getBiddings(int userId) {
		
		try {
			System.out.println("getting the biddings");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("userId", Integer.toString(userId));
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.GET_BIDDINGS);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.GET_BIDDINGS));
			msg = systemAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<Bid> biddings = (ArrayList<Bid>) msg.getContentObject();
				return biddings;
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
	
	private void showInGUI(ArrayList<Bid> biddings) {
		System.out.println("in showing getBiddings");
		int lenBids = biddings.size();
		System.out.println("lenBids " + lenBids);
		if (lenBids == 0)
			return;
		String column[] = { "Index", "clientName", "providerName", "amount", "accept", "reject" };
		Object[][] data = new Object[lenBids][6];
		Bid tmpBid;
		for (int i = 0; i < lenBids; i++) {
			tmpBid = biddings.get(i);
			System.out.println("tmpBid is " + tmpBid);
			Object rowData[] = { tmpBid.getId(), tmpBid.getClientId(), tmpBid.getProviderId(), tmpBid.getAmount(), tmpBid.getAccepted().equals(1), tmpBid.getAccepted().equals(2)};
			System.out.println(rowData);
			for (int j = 0; j < 6; j++) {
				data[i][j] = rowData[j];
			}
		}
		
		JTable table = new JTable(new BidTable(data, systemAgent, user));
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		JScrollPane scrollPane = new JScrollPane(table);

		table.setFillsViewportHeight(true);

		frame1.getContentPane().add(table);
		frame1.setVisible(true);

		frame1.getContentPane().add(BorderLayout.CENTER, table);
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
