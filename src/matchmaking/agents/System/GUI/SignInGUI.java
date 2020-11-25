package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SignInGUI extends JFrame {
//	private user;
	

	
	private   JTextField nameTxt,userTypeTxt,userNameTxt,passwordTxt,accountTypeTxt, 
	emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt,logoTxt,cvTxt;

	public SignInGUI() {

		

		// Creating the Frame
		JFrame frame = new JFrame("SignInGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);

		
		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton signIn = new JButton("signIn");
		JButton signUp = new JButton("signUp");

		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {

					System.out.println("hit the signIn button");
					UserGUI userGui=new UserGUI();
					userGui.showGui();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(SignInGUI.this, "Invalid values. " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {

					System.out.println("hit the signIn button");
					SignUpGUI signUpGUI=new SignUpGUI();
					signUpGUI.showGui();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(SignInGUI.this, "Invalid values. " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panel.add(signIn);
		panel.add(signUp);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(3, 2));
		
	
		
		JLabel userName = new JLabel("userName");
		userNameTxt = new JTextField(20);
		p.add(userName);
		p.add(userNameTxt);
		
		
		JLabel password = new JLabel("password");
		passwordTxt = new JTextField(20);
		p.add(password);
		p.add(passwordTxt);
		
		
		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
	
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
