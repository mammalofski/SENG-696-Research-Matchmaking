
package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.Profiler;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ContractGUI extends JFrame {

	JTextField amountTxt;
	private SystemAgent systemAgent;
	private User user;
	private MatchmakingContract contract;

	public ContractGUI(SystemAgent agent, User user1, MatchmakingContract contract1) {
		System.out.println("creating the contractGUI");
		
		systemAgent = agent;
		user = user1;
		contract = contract1;
		

		JFrame frame = new JFrame("ContractGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);

		JPanel panel = new JPanel();

		JButton reject = new JButton("reject");
		JButton accept = new JButton("accept");

		reject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("the contract has been rejected.");
			}
		});

		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("the contract has been accepted.");
			}
		});

		panel.add(accept);
		panel.add(reject);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(1, 1));

		/*
		 * JLabel projectName = new JLabel("ProjectName"); p.add(projectName);
		 * 
		 * JLabel amount = new JLabel("Amount"); p.add(amount);
		 * 
		 * JLabel deadline = new JLabel("Deadline"); p.add(deadline);
		 */
		
		String contractingUserName = contract.getClientId() == user.getId() ? contract.getProviderName() : contract.getClientName();

		String labelcontent = "<html>Do you confrim to accept the contract between you and " + contractingUserName + "<br/>"
				+ "with the amount  " + contract.getAmount();
		JLabel content = new JLabel(labelcontent,SwingConstants.CENTER);
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
