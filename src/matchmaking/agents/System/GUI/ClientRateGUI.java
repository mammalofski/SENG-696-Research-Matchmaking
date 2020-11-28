
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

public class ClientRateGUI extends JFrame {

	

	public ClientRateGUI() {

		JFrame frame = new JFrame("ClientRateGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);

		JPanel panel = new JPanel();
		JButton ok = new JButton("ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ContractGUI contractGUI = new ContractGUI();
				contractGUI.showGui();

			}
		});
		panel.add(ok);

		JPanel p = new JPanel(); // the panel is not visible in output

		String data[][] = { { "1", "Amit", "A@gmail.com", "c#" }, { "2", "Jai", "b@gmail.com", "javascript" },
				{ "3", "Sachin", "c@gmail.com", "java" } };

		String column[] = { "Index", "Comment", "Rate", "Date" };
		JTable jt = new JTable(data, column);
		jt.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
//		//jt.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jt);
		p.add(sp);

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
