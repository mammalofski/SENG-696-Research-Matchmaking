package matchmaking.GUI;

import jade.core.AID;
import matchmaking.orm.User;
import matchmaking.GUI.UserViewModel;
import matchmaking.orm.DataBase;
import matchmaking.agents.MatchmakerAgent;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class UserGUI extends JFrame {
	

	
	private   JTextField nameTxt,userTypeTxt,userNameTxt,passwordTxt,accountTypeTxt, 
	emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt,logoTxt,cvTxt;

	public UserGUI() {

		

		// Creating the Frame
		JFrame frame = new JFrame("UserGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);

		// Creating the MenuBar and adding components
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("Profile");
		JMenu m2 = new JMenu("UserList");
		JMenu m3 = new JMenu("Bids");
		JMenu m4 = new JMenu("projectProgress");
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		JMenuItem m11 = new JMenuItem("show Profile");
		m1.add(m11);
		m11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("menu item has clicked");
				JFrame frame1 = new JFrame("My First GUI");
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setSize(300, 300);
				JButton button1 = new JButton("Button 1");
				JButton button2 = new JButton("Button 2");
				frame1.getContentPane().add(button1);
				frame1.getContentPane().add(button2);
				frame1.setVisible(true);
			}
		});

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton save = new JButton("save");
		JButton cancel = new JButton("validate");

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {

					System.out.println("hit the save button");
					saveUser();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(UserGUI.this, "Invalid values. " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panel.add(save);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(12, 2));
		
		JLabel name=new JLabel("name");
		nameTxt=new JTextField(20);
		p.add(name);
		p.add(nameTxt);
		
		JLabel userType = new JLabel("userType");
		userTypeTxt = new JTextField(20);
		p.add(userType);
		p.add(userTypeTxt);
		
		
		JLabel userName = new JLabel("userName");
		userNameTxt = new JTextField(20);
		p.add(userName);
		p.add(userNameTxt);
		
		
		JLabel password = new JLabel("password");
		passwordTxt = new JTextField(20);
		p.add(password);
		p.add(passwordTxt);
		
		JLabel accountType = new JLabel("accountType");
		accountTypeTxt = new JTextField(20);
		p.add(accountType);
		p.add(accountTypeTxt);
		
		
		JLabel email = new JLabel("email");
		emailTxt = new JTextField(20);
		p.add(email);
		p.add(emailTxt);

		JLabel hourlyCompensation = new JLabel("hourlyCompensation");
		hourlyCompensationTxt = new JTextField(20);
		p.add(hourlyCompensation);
		p.add(hourlyCompensationTxt);

		JLabel specialKeywords = new JLabel("specialKeywords");
		specialKeywordsTxt = new JTextField(20);
		p.add(specialKeywords);
		p.add(specialKeywordsTxt);

		JLabel website = new JLabel("website");
		websiteTxt = new JTextField(20);
		p.add(website);
		p.add(websiteTxt);
		
		
		
		JLabel logo = new JLabel("logo");
		logoTxt = new JTextField(20);
		p.add(logo);
		p.add(logoTxt);
		
		
		JLabel cv = new JLabel("cv");
		cvTxt = new JTextField(20);
		p.add(cv);
		p.add(cvTxt);
		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	private void saveUser() {

		String name = nameTxt.getText().trim();
		int userType = Integer.parseInt(userTypeTxt.getText().trim());
		String userName = userNameTxt.getText().trim();
		String password = passwordTxt.getText().trim();
		String email = emailTxt.getText().trim();
		int accountType = Integer.parseInt(accountTypeTxt.getText().trim());
		int hourlyCompensation = Integer.parseInt(hourlyCompensationTxt.getText().trim());
		String specialKeywords = specialKeywordsTxt.getText().trim();
		String website = websiteTxt.getText().trim();
		String logo = logoTxt.getText().trim();
		String cv = cvTxt.getText().trim();
		
		System.out.println("this is being saved, name :" + name);
		User user = new User(name, userType, email, userName, password, false, accountType, hourlyCompensation, 
				specialKeywords, logo, website, cv);
		
		user.createUser();

		/// if user registered
		SystemContractGUI systemContractGUI = new SystemContractGUI();
		systemContractGUI.showGui();

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
