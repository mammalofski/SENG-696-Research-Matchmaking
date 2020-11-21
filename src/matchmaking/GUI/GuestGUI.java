package matchmaking.GUI;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GuestGUI extends JFrame {
	private JTextField nameTxt, emailTxt, hourlyCompensationTxt, specialKeywordsTxt, websiteTxt;

	public GuestGUI() {

		System.out.println("This is guset GUI");

		// Creating the Frame
		JFrame frame = new JFrame("Guest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 1200);

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel();
		 
		

		String data[][] = { { "1", "Amit", "A@gmail.com", "c#"}, 
				{ "2", "Jai", "b@gmail.com" , "javascript"}, 
				{ "3", "Sachin", "c@gmail.com" , "java"} };
		
		String column[] = {"ID",  "Name", "Email", "SpecialKeywords"};
		JTable jt = new JTable(data, column);
		//jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		panel.add(sp);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(7, 2));

		JLabel name = new JLabel("name");
		nameTxt = new JTextField(20);
		p.add(name);
		p.add(nameTxt);
		
		
		
		

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
		
		//JLabel advanceSearch = new JLabel("Advance Search");
		//p.add(advanceSearch);
		JButton search = new JButton("search");
		p.add(search);
		

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
