
package matchmaking.agents.System.GUI;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
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
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ClientRateGUI extends JFrame {
	private User user;
	private SystemAgent myAgent;
	

	public ClientRateGUI(SystemAgent agent, User user1) {
		
		myAgent = agent;
		user = user1;
		
		JFrame frame = new JFrame("ClientRateGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 600);

		JPanel panel = new JPanel();
		JButton ok = new JButton("ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
//				ContractGUI contractGUI = new ContractGUI();
//				contractGUI.showGui();

			}
		});
		panel.add(ok);
		
		ArrayList<Feedback> feedbacks = getFeedbacks();
		String data[][] = convertFeedbacksToData(feedbacks);

		JPanel p = new JPanel(); // the panel is not visible in output

//		String data[][] = { { "1", "Amit", "A@gmail.com", "c#" }, { "2", "Jai", "b@gmail.com", "javascript" },
//				{ "3", "Sachin", "c@gmail.com", "java" } };

		String column[] = { "Index", "Sender", "Comment", "Rate", "Date" };
		JTable jt = new JTable(data, column);
		jt.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
//		//jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		p.add(sp);

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
	
	private ArrayList<Feedback> getFeedbacks() {
		try {
			ACLMessage msg, reply;
			System.out.println("getting the messages");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("userId", Integer.toString(user.getId()));
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.GET_FEEDBACKS);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
			
			MessageTemplate template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.GET_FEEDBACKS));
			msg = myAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<Feedback> feedbacks = (ArrayList<Feedback>) msg.getContentObject();
				return feedbacks;
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
	
	
	private String[][] convertFeedbacksToData(ArrayList<Feedback> feedbacks) {
		int lenFeedback= feedbacks.size();
		String[][] data = new String[lenFeedback][5];
		Feedback tmpFeedback;
		for (int i = 0; i < lenFeedback; i++) {
			tmpFeedback = feedbacks.get(i);
			String rowData[] = {Integer.toString(i), Integer.toString(tmpFeedback.getSenderId()), tmpFeedback.getComment(), Integer.toString(tmpFeedback.getRate()), tmpFeedback.getDate()};
			for (int j = 0; j < 5; j++) {
				data[i][j] = rowData[j];
			}
		}
		return data;
	}

}
