
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

public class SearchUserGUI extends JFrame {
	private JTextField nameTxt, emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt;
	private AID myAgentAID;

	private ACLMessage msg, reply;
	private MessageTemplate template;

	private SystemAgent systemAgent;
	private JFrame frame;
	private JPanel p;
	private User user;

	public SearchUserGUI(SystemAgent agent, User user1) {

		System.out.println("This is Search User GUI");
		user = user1;
		myAgentAID = new AID("SystemAgent", AID.ISLOCALNAME);
		systemAgent = agent;
		System.out.println("system agent's aid is " + myAgentAID);

		// Creating the Frame
		frame = new JFrame("SearchUserGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 900);

		p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(6 ,2));

		JLabel name = new JLabel("name");
		nameTxt = new JTextField(20);
		p.add(name);
		p.add(nameTxt);

		JLabel emailLabel = new JLabel("email");
		emailTxt = new JTextField(20);
		p.add(emailLabel);
		p.add(emailTxt);

		JLabel hourlyCompensationLabel = new JLabel("hourlyCompensation");
		hourlyCompensationTxt = new JTextField(20);
		p.add(hourlyCompensationLabel);
		p.add(hourlyCompensationTxt);

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

		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ArrayList<User> usersResult = search();
				System.out.println("passing users to showInGUI");
				showInGUI(usersResult);
			}
		});

//		JTable table = new JTable(new CustomJTable());
//		JScrollPane scrollPane = new JScrollPane(table);
//		table.setFillsViewportHeight(true);
//
//		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
//		table.getColumn("Place a bid").setCellRenderer(buttonRenderer);
//
//		JPanel panel = new JPanel();
//		panel.add(table);
//		// Adding Components to the frame.
//		frame.getContentPane().add(BorderLayout.SOUTH, panel);
//		frame.getContentPane().add(BorderLayout.CENTER, p);
//		frame.setVisible(true);
	}

	private ArrayList<User> search() {

		try {
			System.out.println("hit the search button");
			String name = nameTxt.getText().trim();
			String email = emailTxt.getText().trim(); //
			String hourlyCompensation = hourlyCompensationTxt.getText().trim();
			String specialKeywords = specialKeywordsTxt.getText().trim();
			String website = websiteTxt.getText().trim(); //
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("name", name);
			requestBody.put("email", email);
			requestBody.put("specialKeywords", specialKeywords);
			requestBody.put("website", website);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.SEARCH);
			String messageText = Constants.GUEST + " " + requestBody; //
			msg.setContent(messageText);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			System.out.println("sent the message to matchmaker");

			// wait for the response
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.SEARCH));
			msg = systemAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<User> users = (ArrayList<User>) msg.getContentObject();
//				showInGUI(users);
				return users;
			}

		} catch (Exception e) {
//			JOptionPane.showMessageDialog(GuestGUI.this, "Invalid values. " + e.getMessage(), "Error",
//					JOptionPane.ERROR_MESSAGE);
		}

		return null;

	}

	private void showInGUI(ArrayList<User> users) {

		int lenUsers = users.size();
		String column[] = { "Index", "Name", "Email", "SpecialKeywords", "website", "isPremium" };
		String[][] data = new String[lenUsers][6];
		User tmpUser;
		String isPremium;
		for (int i = 0; i < lenUsers; i++) {
			tmpUser = users.get(i);
			isPremium = tmpUser.getAccountType() == 1 ? "true" : "false";
			String rowData[] = { Integer.toString(tmpUser.getId()), tmpUser.getEmail(), tmpUser.getName(),
					tmpUser.getSpecialKeyword(), Integer.toString(tmpUser.gethourlyCompensation()), isPremium };
			System.out.println(rowData);
			for (int j = 0; j < rowData.length; j++) {
				data[i][j] = rowData[j];
			}
		}

		JTable table = new JTable(new CustomJTable(data, systemAgent, user));
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Place a bid").setCellRenderer(buttonRenderer);
		table.getColumn("ShowRates").setCellRenderer(buttonRenderer);

		JPanel panel = new JPanel();
		panel.add(table);
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
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
