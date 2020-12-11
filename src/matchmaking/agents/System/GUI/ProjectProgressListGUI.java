

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

public class ProjectProgressListGUI extends JFrame {
	private ACLMessage msg, reply;
	private MessageTemplate template;
	JFrame frame1;
	private User user;
	private SystemAgent systemAgent;
	public ProjectProgressListGUI (SystemAgent agent,User currentUser)
	{
		user=currentUser;
		systemAgent=agent;
		
		frame1 = new JFrame("ProjectProgressList");
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.setSize(400, 400);
		
		frame1.setVisible(true);

		ArrayList<Project> projects = getProjects(user.getId());
		showInGUI(projects);
	}
private ArrayList<Project> getProjects(int userId) {
		
		try {
			System.out.println("getting the project");
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("userId", Integer.toString(userId));
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.GET_PROJECTS);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.GET_PROJECTS));
			msg = systemAgent.blockingReceive(template);
			if (msg != null) { //
				ArrayList<Project> projects = (ArrayList<Project>) msg.getContentObject();
				return projects;
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
	

private void showInGUI(ArrayList<Project> projects) {
		
		int lenprojects = projects.size();
		System.out.println("lenprojects " + lenprojects);
		if (lenprojects == 0)
			return;
		String column[] = { "Index", "projectName" };
		String[][] data = new String[lenprojects][2];
		Project tempProject;
		for (int i = 0; i < lenprojects; i++) {
			tempProject = projects.get(i);
			String rowData[] = {Integer.toString(tempProject.getId()), tempProject.getName()};	
			System.out.println(rowData);
			for (int j = 0; j < 2; j++) {
				data[i][j] = rowData[j];
			}
		}
		
		JTable table = new JTable(new ProjectProgressJTable(data, systemAgent, user, projects));
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("ShowProject").setCellRenderer(buttonRenderer);
		table.getColumn("RateProject").setCellRenderer(buttonRenderer);
		
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

