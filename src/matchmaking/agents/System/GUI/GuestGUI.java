package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
import java.util.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jade.lang.acl.ACLMessage;
import matchmaking.agents.System.SystemAgent;
import jade.core.AID;
import jade.core.Agent;
import matchmaking.orm.Constants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GuestGUI extends JFrame {
	private JTextField nameTxt, emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt;
	private AID myAgentAID;
	private SystemAgent myAgent;

	public GuestGUI(SystemAgent agent) {

		System.out.println("This is guset GUI");
		
		myAgent = agent;
		myAgentAID = new AID("SystemAgent", AID.ISLOCALNAME);
		System.out.println("system agent's aid is " + myAgentAID);
//		myAgent = localContainer.acquireLocalAgent(aid);

		// Creating the Frame
		JFrame frame = new JFrame("Guest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 1200);

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel();
		 
		

		String data[][] = { { "1", "Amit", "A@gmail.com", "c#"}, 
				{ "2", "Jai", "b@gmail.com" , "javascript"}, 
				{ "3", "Sachin", "c@gmail.com" , "java"} };
		
		String column[] = {"ID",  "Name", "Email", "SpecialKeywords"};
		JTable jt = new JTable(data, column);
		//jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		panel.add(sp);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(7, 2));

		JLabel name = new JLabel("name");
		nameTxt = new JTextField(20);
		p.add(name);
		p.add(nameTxt);
		
		
		
		

		JLabel emailLabel = new JLabel("email");
		emailTxt = new JTextField(20);
		p.add(emailLabel);
		p.add(emailTxt);

//		JLabel hourlyCompensationLabel = new JLabel("hourlyCompensation");
//		hourlyCompensationTxt = new JTextField(20);
//		p.add(hourlyCompensationLabel);
//		p.add(hourlyCompensationTxt);

		JLabel specialKeywordsLabel = new JLabel("specialKeywords");
		specialKeywordsTxt = new JTextField(20);
		p.add(specialKeywordsLabel);
		p.add(specialKeywordsTxt);

		JLabel websiteLabel = new JLabel("website");
		websiteTxt = new JTextField(20);
		p.add(websiteLabel);
		p.add(websiteTxt);
		
		//JLabel advanceSearch = new JLabel("Advance Search");
		//p.add(advanceSearch);
		JButton search = new JButton("search");
		p.add(search);
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				search();
		}});
		

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}
	
	private void search() {
		try {
			System.out.println("hit the search button");
			String email = emailTxt.getText().trim();
//			String jourlyCompensation = jourlyCompensationTxt.getText().trim();
			String specialKeywords = specialKeywordsTxt.getText().trim();
			String website = websiteTxt.getText().trim();
//			String requestBody = "body: {user=guest, email=" + email + ", specialKeywords=" + specialKeywords + ", website=" + website + "}";;
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("email", email);
			requestBody.put("specialKeywords", specialKeywords);
			requestBody.put("website", website);
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.SEARCH);;
//			String messageText = Constants.GUEST + " " + requestBody;
//			msg.setContent(messageText);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
			System.out.println("sent the message to matchmaker");
			
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(GuestGUI.this, "Invalid values. " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	
		
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
