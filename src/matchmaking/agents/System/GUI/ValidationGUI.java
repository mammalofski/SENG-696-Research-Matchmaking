

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

public class ValidationGUI extends JFrame {

	JTextField amountTxt;
	private SystemAgent systemAgent;
	private User user;

	public ValidationGUI(User user1) {
		
		
		
		user = user1;
		

		System.out.println("validateUser");
		user.validate();
		JFrame validationFrame = new JFrame("ValidationGUI");
		validationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		validationFrame.setSize(300, 100);
		JPanel panel=new JPanel();
		JPanel panel1=new JPanel();

		JLabel validationLabel = new JLabel("are you validted? please be honest :)");
		JButton validationBtn = new JButton("validate");

		validationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				user.validate();
				validationFrame.dispose();
			}
		});
		panel.add(validationLabel);
		panel1.add(validationBtn);

		validationFrame.getContentPane().add(BorderLayout.SOUTH, panel1);
		validationFrame.getContentPane().add(BorderLayout.CENTER, panel);
		
		validationFrame.setVisible(true);
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

