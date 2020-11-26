package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
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

public class GuestGUI extends JFrame {
	private JTextField nameTxt, emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt;
	private AID myAgentAID;
	private SystemAgent myAgent;
	private ACLMessage msg, reply;
	private MessageTemplate template;
	private JPanel panel;

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
		panel = new JPanel();

//		String data[][] = { { "1", "Amit", "A@gmail.com", "c#" }, { "2", "Jai", "b@gmail.com", "javascript" },
//				{ "3", "Sachin", "c@gmail.com", "java" } };
//
//		String column[] = { "ID", "Name", "Email", "SpecialKeywords" };
//		JTable jt = new JTable(data, column);
//		jt.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
//		//jt.setBounds(30, 40, 200, 300);
//		JScrollPane sp = new JScrollPane(jt);
//		panel.add(sp);

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

		// JLabel advanceSearch = new JLabel("Advance Search");
		// p.add(advanceSearch);
		JButton search = new JButton("search");
		p.add(search);

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ArrayList<User> usersResult = search();
//				showInGUI(userResults);
			}
		});

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	private ArrayList<User> search() {

		try {
			System.out.println("hit the search button");
			String name = nameTxt.getText().trim();
			String email = emailTxt.getText().trim();
//			String jourlyCompensation = jourlyCompensationTxt.getText().trim();
			String specialKeywords = specialKeywordsTxt.getText().trim();
			String website = websiteTxt.getText().trim();
//			String requestBody = "body: {user=guest, email=" + email + ", specialKeywords=" + specialKeywords + ", website=" + website + "}";;
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("name", name);
			requestBody.put("email", email);
			requestBody.put("specialKeywords", specialKeywords);
			requestBody.put("website", website);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.SEARCH);
			;
//			String messageText = Constants.GUEST + " " + requestBody;
//			msg.setContent(messageText);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
			System.out.println("sent the message to matchmaker");

			// wait for the response
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.SEARCH));
			msg = myAgent.blockingReceive(template);
			if (msg != null) {
//				ArrayList = msg.getContentObject();
				ArrayList<User> users = (ArrayList<User>) msg.getContentObject();
				showInGUI(users);
				return users;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(GuestGUI.this, "Invalid values. " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;

	}

	private void showInGUI(ArrayList<User> users) {

		String data2[][] = { { "1", "Amit", "A@gmail.com", "c#" }, { "2", "Jai", "b@gmail.com", "javascript" },
				{ "3", "Sachin", "c@gmail.com", "java" } };
//		String data[][];
//
//		String column[] = { "ID", "Name", "Email", "SpecialKeywords" };
//		JTable jt = new JTable(data, column);
//		// jt.setBounds(30, 40, 200, 300);
//		JScrollPane sp = new JScrollPane(jt);
//		panel.add(sp);
		int lenUsers = users.size();
		String column[] = { "Index", "Name", "Email", "SpecialKeywords", "website", "isPremium" };
//		String[][] = new data[][];
		String[][] data = new String[lenUsers][6];
		User tmpUser;
		String isPremium;
		for (int i = 0; i < lenUsers; i++) {
//			System.out.println("1");
			tmpUser = users.get(i);
			isPremium = tmpUser.getAccountType() == 1 ? "premium" : "free";
			String rowData[] = {Integer.toString(i), tmpUser.getName(), tmpUser.getEmail(), tmpUser.getSpecialKeyword(), tmpUser.getWebsite(), isPremium};
			System.out.println(rowData);
			for (int j=0; j < rowData.length; j++) {
//				System.out.println("2");
				data[i][j] = rowData[j];
			}
		}
		
		JTable jt = new JTable(data, column);
		jt.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		// jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		panel.add(sp);

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
