package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BidsListGUI extends JFrame {
	public BidsListGUI() {

		System.out.println("BidsListGUI");
		JFrame frame1 = new JFrame("BidsListGUI");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(400, 400);

		JTable table = new JTable(new BidTable());
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		JScrollPane scrollPane = new JScrollPane(table);

		table.setFillsViewportHeight(true);

		frame1.getContentPane().add(table);
		frame1.setVisible(true);

		frame1.getContentPane().add(BorderLayout.CENTER, table);

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
