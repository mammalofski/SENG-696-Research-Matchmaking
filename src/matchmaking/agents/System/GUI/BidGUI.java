package matchmaking.agents.System.GUI;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.Profiler;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class BidGUI extends JFrame {
	
	private int userId;
	private ACLMessage msg, reply;
	Hashtable<String, String> requestBody = new Hashtable<String, String>();
	SystemAgent systemAgent;
	private User clientUser;

	JTextField amountTxt;
	public BidGUI(int userId1, SystemAgent agent, User user1) {
		userId = userId1; // this is the provider
		clientUser = user1;  // this is the client placing bid
		systemAgent = agent;
		JFrame frame = new JFrame("BidGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		
		JPanel panel = new JPanel(); 

		JButton cancel = new JButton("cancel");
		JButton placeABid = new JButton("place a bid");
	

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});
		
		placeABid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				try {
					System.out.println("Sace the bid");
					String biddingAmount = amountTxt.getText().trim();
					requestBody.put("userId", Integer.toString(userId));
					requestBody.put("biddingAmount", biddingAmount);
					requestBody.put("clientId", Integer.toString(clientUser.getId()));
					msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setConversationId(Constants.PLACE_BID);
					msg.setContentObject(requestBody);
					msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
					systemAgent.send(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		
		panel.add(placeABid);
		panel.add(cancel);
		
		

		JPanel	p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(2, 2));
		
		
		 JLabel amount = new JLabel("Amount");
		  amountTxt = new JTextField(20);
		  p.add(amount);
		  p.add(amountTxt);
		 

		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		;
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
