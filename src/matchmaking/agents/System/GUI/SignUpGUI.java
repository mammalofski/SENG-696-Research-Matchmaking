package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.SystemAgent;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

public class SignUpGUI extends JFrame implements ActionListener {
//	private user;

	private JTextField nameTxt, userTypeTxt, userNameTxt, passwordTxt, accountTypeTxt, emailTxt, hourlyCompensationTxt,
			specialKeywordsTxt, websiteTxt, logoTxt, cvTxt;
	
	private SystemAgent systemAgent;

	public SignUpGUI(SystemAgent agent) {
		systemAgent = agent;
		// Creating the Frame
		JFrame frame = new JFrame("SignUpGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);

		
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
					JOptionPane.showMessageDialog(SignUpGUI.this, "Invalid values. " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panel.add(save);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(12, 2));

		JLabel name = new JLabel("name");
		nameTxt = new JTextField(20);
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

//		JLabel accountType = new JLabel("accountType");
//		accountTypeTxt = new JTextField(20);
//		p.add(accountType);
//		p.add(accountTypeTxt);

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
		JButton logoBtn = new JButton("addLogo");
		p.add(logo);
		p.add(logoBtn);

		logoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				saveLogo();
			}

		});

		JLabel cv = new JLabel("cv");
		JButton cvBtn = new JButton("addCv");
		p.add(cv);
		p.add(cvBtn);

		cvBtn.addActionListener(this);

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		
		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setVisible(true);
	}

	public void saveLogo() {
		JFileChooser fc = new JFileChooser();
		int i = fc.showOpenDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String filepath = f.getPath();
			try {
				BufferedReader br = new BufferedReader(new FileReader(filepath));
				String s1 = "", s2 = "";
				while ((s1 = br.readLine()) != null) {
					s2 += s1 + "\n";
				}
				System.out.println("this is s1:" + s1);
				System.out.println("this is s2:" + s2);
				br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent ev) {
		try {

			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String filepath = f.getPath();
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "", s2 = "";
					while ((s1 = br.readLine()) != null) {
						s2 += s1 + "\n";
					}
					System.out.println("this is s1:" + s1);
					System.out.println("this is s2:" + s2);
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(SignUpGUI.this, "Invalid values. " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveUser() {

		String name = nameTxt.getText().trim();
		int userType = Integer.parseInt(userTypeTxt.getText().trim());
		String userName = userNameTxt.getText().trim();
		String password = passwordTxt.getText().trim();
		String email = emailTxt.getText().trim();
//		int accountType = Integer.parseInt(accountTypeTxt.getText().trim());
		int hourlyCompensation = Integer.parseInt(hourlyCompensationTxt.getText().trim());
		String specialKeywords = specialKeywordsTxt.getText().trim();
		String website = websiteTxt.getText().trim();
//		String logo = logoTxt.getText().trim();
//		String cv = cvTxt.getText().trim();

		System.out.println("this is being saved, name :" + name);
		User user = new User(name, userType, email, userName, password, false, 0, hourlyCompensation,
				specialKeywords, "", website, "");

		user.createUser();
		
		UserGUI userGUI=new UserGUI(user, systemAgent);
		userGUI.showGui();

		/// if user registered
		SystemContractGUI systemContractGUI = new SystemContractGUI(user);
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
