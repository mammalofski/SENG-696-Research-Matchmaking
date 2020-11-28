
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

public class ProjectProgressGUI extends JFrame {

	JTextField nameTxt, descriptionTxt, deadlineTxt, progressTxt, stateTxt;

	public ProjectProgressGUI() {

		JFrame frame = new JFrame("ProjectProgressGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);

		JPanel panel = new JPanel();

		JButton cancel = new JButton("cancel");
		JButton ok = new JButton("ok");

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ok");

			}
		});

		panel.add(ok);
		panel.add(cancel);

		JPanel p = new JPanel(); // the panel is not visible in output
		p.setLayout(new GridLayout(5, 2));

		JLabel name = new JLabel("Name");
		nameTxt = new JTextField(20);
		p.add(name);
		p.add(nameTxt);
		
		JLabel description = new JLabel("Description");
		descriptionTxt = new JTextField(20);
		p.add(description);
		p.add(descriptionTxt);

		JLabel deadline = new JLabel("Deadline");
		deadlineTxt = new JTextField(20);
		p.add(deadline);
		p.add(deadlineTxt);
		
		
		JLabel progress = new JLabel("Progress");
		progressTxt = new JTextField(20);
		p.add(progress);
		p.add(progressTxt);
		
		JLabel state = new JLabel("State");
		stateTxt = new JTextField(20);
		p.add(state);
		p.add(stateTxt);
		
		
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
