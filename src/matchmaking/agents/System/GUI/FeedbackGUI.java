
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

public class FeedbackGUI extends JFrame {

	JTextField commentTxt, rateTxt;
	SystemAgent myAgent;
	User user;
	Project project;

	public FeedbackGUI(SystemAgent agent, User user1, Project project1) {
		myAgent = agent;
		user = user1;
		project = project1;

		JFrame frame = new JFrame("FeedbackGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 200);

		JPanel panel = new JPanel();

		JButton cancel = new JButton("cancel");
		JButton save = new JButton("Save");

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("changeDeadline");

				submibFeedback();

			}
		});

		panel.add(save);
		panel.add(cancel);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		JLabel comment = new JLabel("Comment");
		commentTxt = new JTextField(20);
		p.add(comment);
		p.add(commentTxt);
		
		JLabel rate = new JLabel("Rate");
		rateTxt = new JTextField(20);
		p.add(rate);
		p.add(rateTxt);;

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		;
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	protected void submibFeedback() {
		try {
			ACLMessage msg;
			System.out.println("requesting to update project.");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			
			requestBody.put("comment", commentTxt.getText().trim());
			requestBody.put("rate", rateTxt.getText().trim());
			requestBody.put("projectId", Integer.toString(project.getId()));
			requestBody.put("userId", Integer.toString(user.getId()));
			
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.SUBMIT_FEEDBACK);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			myAgent.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
