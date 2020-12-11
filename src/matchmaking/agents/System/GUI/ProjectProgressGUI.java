
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

public class ProjectProgressGUI extends JFrame {

	JTextField nameTxt, descriptionTxt, deadlineTxt, progressTxt, stateTxt;
	SystemAgent myAgent;
	User user;
	Project project;
	private JLabel descriptionLabel;

	public ProjectProgressGUI(SystemAgent agent, User user1, Project project1) {
		myAgent = agent;
		user = user1;
		project = project1;

		JFrame frame = new JFrame("ProjectProgressGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 600);
		

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

				requestForUpdateProject();

			}
		});

		panel.add(save);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(5, 2));

		JLabel name = new JLabel("Name");
		JLabel projectName = new JLabel(project.getName());
		p.add(name);
		p.add(projectName);

		JLabel state = new JLabel("State");
		String stateStr = project.getState() == Constants.ProjectState.DONE ? "Finished" : "In Progress";
		JLabel projectState = new JLabel(stateStr);
		p.add(state);
		p.add(projectState);
		
		

		if (user1.getuserType() == Constants.UserTypes.PROVIDER) {

			JLabel progress = new JLabel("Progress");
			progressTxt = new JTextField(20);
			progressTxt.setText(Integer.toString(project.getProgress()));
			p.add(progress);
			p.add(progressTxt);
			JLabel deadline = new JLabel("Deadline");
			deadlineTxt = new JTextField(20);
			deadlineTxt.setText(project.getDeadline());
			p.add(deadline);
			p.add(deadlineTxt);
			JLabel description = new JLabel("description");
			descriptionLabel = new JLabel(project.getDescription());
			p.add(description);
			p.add(descriptionLabel);
		} else {

			System.out
					.println("deadline is " + project.getDeadline() + "and description is " + project.getDescription());
			JLabel progress = new JLabel("Progress");
			JLabel progressLabel = new JLabel(Integer.toString(project.getProgress()));
			p.add(progress);
			p.add(progressLabel);
			JLabel description = new JLabel("description");
			descriptionTxt = new JTextField(20);
			descriptionTxt.setText(project.getDescription());
			p.add(description);
			p.add(descriptionTxt);
			JLabel deadline = new JLabel("deadline");
			JLabel deadlineLabel = new JLabel(project.getDeadline());
			p.add(deadline);
			p.add(deadlineLabel);
		}

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		;
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
		
		if (user.getuserType() == Constants.UserTypes.PROVIDER && !project.getNewDescription().equals("")
				&& !project.getNewDescription().equals(null)) {
			ApproveGUI approveGui = new ApproveGUI(myAgent, user, project,descriptionLabel);
			approveGui.showGui();
		}
	}

	protected void requestForUpdateProject() {
		try {
			ACLMessage msg;
			System.out.println("requesting to update project.");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("requester", Integer.toString(user.getuserType()));
			requestBody.put("projectId", Integer.toString(project.getId()));
			requestBody.put("type", "requestForUpdate");
			if (user.getuserType() == Constants.UserTypes.CLIENT) {
				requestBody.put("description", descriptionTxt.getText().trim());
			} else {
				requestBody.put("deadline", deadlineTxt.getText().trim());
				requestBody.put("progress", progressTxt.getText().trim());
			}
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.UPDATE_PROJECT);
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
