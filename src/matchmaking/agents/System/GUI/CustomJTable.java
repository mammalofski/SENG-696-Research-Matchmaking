package matchmaking.agents.System.GUI;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import matchmaking.agents.System.SystemAgent;
import matchmaking.orm.User;

public class CustomJTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static String[] COLUMN_NAMES = new String[] { "index", "email", "clientName", "specialKeywords",
			"hourlyCompensation", "isPremium", "Place a bid", "ShowRates" };
	private static Class<?>[] COLUMN_TYPES = new Class<?>[] { Integer.class, String.class, String.class, String.class,
			Integer.class, Boolean.class, JButton.class, JButton.class };
	private String[][] data;
	private SystemAgent systemAgent;
	private User user;

	CustomJTable(String[][] data1, SystemAgent agent, User user1) {
		super();
		data = data1;
		systemAgent = agent;
		user = user1;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public void setData(String[][] data1) {
		data = data1;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int row, int col) {

		if (col > 5) {

			String data1 = "true";
			setValueAt(data1, row, col);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		System.out.println("value : " + value);
		if (col == 6) {
			BidGUI bidGUI = new BidGUI(Integer.parseInt(data[row][0]), systemAgent, user);
			bidGUI.showGui();
		} else if (col == 7) {
			ClientRateGUI clientRateGUI = new ClientRateGUI(systemAgent, user);
			clientRateGUI.showGui();
		}

	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		/* Adding components */

//		if (columnIndex == 0) {
//			return rowIndex;
//		} else if (columnIndex < 7) {
//			return data[rowIndex][columnIndex];
//		} else if (columnIndex == 7) {
//			final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
//			return button;
//		} else {
//			return "Error";
//		}

		switch (columnIndex) {
		case 0:
			return rowIndex;
		case 1:
			return data[rowIndex][columnIndex];
		case 2:
			return data[rowIndex][columnIndex];
		case 3:
			return data[rowIndex][columnIndex];
		case 4:
			return Integer.parseInt(data[rowIndex][columnIndex]);
		case 5:
			return data[rowIndex][columnIndex] == "1";
		case 6:
			final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
			return button;
		case 7:
			final JButton button1 = new JButton(COLUMN_NAMES[columnIndex]);
			return button1;
		default:
			return "";

		}

	}

}
