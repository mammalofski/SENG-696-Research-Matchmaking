package matchmaking.agents.System.GUI;

import jade.core.AID;
import matchmaking.orm.*;
import matchmaking.agents.Matchmaker.MatchmakerAgent;
import matchmaking.agents.System.Profiler;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class UserGUI extends JFrame {
	private User user;
	JCheckBox isPremium;
	JFrame paymentFrame;
	JTextField nameTxt, userTypeTxt, specialKeywordsTxt, emailTxt, hourlyCompensationTxt, websiteTxt;
	Profiler profiler;
	JPanel p;

	public UserGUI(User user1) {
//	public UserGUI() {
		user = user1;
		profiler = new Profiler();
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
		JMenuItem m22 = new JMenuItem("Show User List");
		m2.add(m22);
		m22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				SearchUserGUI searchUserGUI = new SearchUserGUI();
				searchUserGUI.showGui();
			}
		});

		JMenuItem m33 = new JMenuItem("Show Bids List");
		m3.add(m33);
		m33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				BidsListGUI bidsListGUI = new BidsListGUI();
				bidsListGUI.showGui();
			}
		});

		JMenuItem m41 = new JMenuItem("Show chat room");
		m4.add(m41);
		m41.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				ChatRoomGUI chatRoomGUI = new ChatRoomGUI();
				chatRoomGUI.showGui();
			}
		});

		JMenuItem m42 = new JMenuItem("Show project progress");
		m4.add(m42);
		m42.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				ProjectProgressGUI projectProgressGUI = new ProjectProgressGUI();
				projectProgressGUI.showGui();
			}
		});
		

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		JButton cancel = new JButton("cancel");
		JButton validate = new JButton("validate");
		JButton updateProfileBtn = new JButton("update profile");

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("validateUser");
				user.validate();
				JFrame validationFrame = new JFrame("Validation");
				validationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				validationFrame.setSize(300, 100);

				JLabel validationLabel = new JLabel("are you validted? please be honest :)");
				JButton validationBtn = new JButton("validate");

				validationBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						user.validate();
						validationFrame.dispose();
					}
				});
				validationFrame.add(validationLabel);
				validationFrame.add(validationBtn);

				validationFrame.getContentPane().add(BorderLayout.SOUTH, validationBtn);
				validationFrame.getContentPane().add(BorderLayout.CENTER, validationLabel);

			}
		});

		updateProfileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("update profile");
				user.setName(nameTxt.getText().trim());
				user.setEmail(emailTxt.getText().trim());
				user.setHourlyCompensation(Integer.parseInt(hourlyCompensationTxt.getText().trim()));
				user.setSpecialKeyword(specialKeywordsTxt.getText().trim());
				user.setWebsite(websiteTxt.getText().trim());
				user.setAccountType(isPremium.isSelected() ? 1 : 0);
				System.out.println("in update profile acc type is " + isPremium.isSelected());
				profiler.updateProfile(user);
			}
		});
		
		panel.add(validate);
		panel.add(updateProfileBtn);
		panel.add(cancel);
		
		p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(12, 2));

		JLabel name = new JLabel("name");
		nameTxt = new JTextField(20);
		nameTxt.setText(user.getName());
		p.add(name);
		p.add(nameTxt);
		
		JLabel userType = new JLabel("userType");
		JLabel userTypeLabel = new JLabel(Integer.toString(user.getuserType())); 
//		userTypeTxt.setText(Integer.toString(user.getuserType()));
		p.add(userType);
		p.add(userTypeLabel);
		

		JLabel email = new JLabel("email");
		emailTxt = new JTextField(20);
		emailTxt.setText(user.getEmail());
		p.add(email);
		p.add(emailTxt);
		

		JLabel hourlyCompensation = new JLabel("hourlyCompensation");
		hourlyCompensationTxt = new JTextField(20);
		hourlyCompensationTxt.setText(Integer.toString(user.gethourlyCompensation()));
		p.add(hourlyCompensation);
		p.add(hourlyCompensationTxt);

		JLabel specialKeywords = new JLabel("specialKeywords");
		specialKeywordsTxt = new JTextField(20);
		specialKeywordsTxt.setText(user.getSpecialKeyword());
		p.add(specialKeywords);
		p.add(specialKeywordsTxt);

		JLabel website = new JLabel("website");
		websiteTxt = new JTextField(20);
		websiteTxt.setText(user.getWebsite());
		p.add(website);
		p.add(websiteTxt);

//		JLabel logo = new JLabel("logo");
//		p.add(logo);
//		ImageIcon image = new ImageIcon("/Users/Saeb/Desktop/T2W/samples/download.png");
//		JLabel imageLabel = new JLabel(image);
//		imageLabel.setBounds(10, 10, 10, 10);
//		imageLabel.setVisible(true);
//		p.add(imageLabel);
//
//		JLabel cv = new JLabel("cv");
//		p.add(cv);
//
//		JButton showCv = new JButton("showCv");
//		p.add(showCv);
		

//		showCv.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ev) {
//				JFrame frame = new JFrame("cv");
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setSize(300, 300);
//
//				JPanel panel = new JPanel();
//
//				frame.getContentPane().add(BorderLayout.CENTER, panel);
//
//				frame.setVisible(true);
//				ImageIcon image1 = new ImageIcon("/Users/Saeb/Desktop/T2W/samples/download.png");
//				JLabel imageLabel1 = new JLabel(image1);
//				// imageLabel1.setBounds(10, 10, 10, 10);
//				imageLabel1.setVisible(true);
//				panel.add(imageLabel1);
//				JScrollPane scrollableTextArea = new JScrollPane(imageLabel1);
//
//				scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//				scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//				frame.getContentPane().add(scrollableTextArea);
//			}
//
//		});

		JLabel accountType = new JLabel("accountType");
		isPremium = new JCheckBox("isPremium");
		Boolean isPremiumAccount = user.getAccountType() == 1;
		isPremium.setSelected(isPremiumAccount);
//		isPremium.setBounds(100, 100, 50, 50);
		p.add(accountType);
		p.add(isPremium);
		
		
		JFrame paymentFrame = new JFrame("PaymentGate");

		isPremium.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() == isPremium) {
					if (e.getStateChange() == 1) { // Creating the
						
						paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						paymentFrame.setSize(300, 100);

						// Creating the panel at bottom and adding components
						JPanel panel = new JPanel(); // the panel is not visible in output
						
						JLabel name = new JLabel("please pay the premium cost.");
						panel.add(name);
						
						JButton paymentBtn = new JButton("Pay");
						panel.add(paymentBtn);
						
						paymentFrame.getContentPane().add(BorderLayout.CENTER, panel);
						paymentFrame.setVisible(true);
						
						paymentBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								paymentFrame.dispose();
							}
						});

					} else {
						paymentFrame.dispose();
					}
				}

			}
		});

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, mb);
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

	public void downgradeToClient() {
		// this method does not work yet!
		// this has to downgrade the user in UserGui after disagreeing with the contract
		JLabel userTypeLabel = new JLabel("1");
		p.add(userTypeLabel);
	}

}
