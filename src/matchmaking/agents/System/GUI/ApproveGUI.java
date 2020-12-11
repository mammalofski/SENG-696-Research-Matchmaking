
package matchmaking.agents.System.GUI;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.Profiler;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ApproveGUI extends JFrame {

	JTextField amountTxt;
	private SystemAgent systemAgent;
	private User user;
	private MatchmakingContract contract;
	private ACLMessage msg, reply;
	private Project project;
	JLabel descriptionLabel;

	public ApproveGUI(SystemAgent agent, User user1, Project project1,JLabel descriptionLabel1) {
		System.out.println("creating the contractGUI");

		systemAgent = agent;
		user = user1;
		project = project1;
		descriptionLabel=descriptionLabel1;

		JFrame frame = new JFrame("ApproveDeadlineGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 300);

		JPanel panel = new JPanel();

		JButton reject = new JButton("reject");
		JButton accept = new JButton("accept");

		reject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("the contract has been rejected.");
				frame.dispose();
			}
		});

		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				try {
					System.out.println("the project update has been accepted.");
					Hashtable<String, String> requestBody = new Hashtable<String, String>();
					requestBody.put("type", "approveUpdate");
					requestBody.put("projectId", Integer.toString(project.getId()));
					msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setConversationId(Constants.UPDATE_PROJECT);
					msg.setContentObject(requestBody);
					msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
					systemAgent.send(msg);
					System.out.println("approve update on project");
					descriptionLabel.addPropertyChangeListener( new PropertyChangeListener(){
						   @Override
						   public void propertyChange( PropertyChangeEvent event ){
							   descriptionLabel.setText(project.getNewDescription() );
						   }
						});
					frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		panel.add(accept);
		panel.add(reject);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(1, 1));

		String labelcontent = "<html>The client wishes to update the description to: <br/> "
				+ project.getNewDescription() + " <br>" + "Do you accept this update on the project?";
		JLabel content = new JLabel(labelcontent, SwingConstants.CENTER);
		p.add(content);

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
