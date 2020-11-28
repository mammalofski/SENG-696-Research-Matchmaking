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

public class CustomJTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = new String[] { "Id", "clientName", "projectName", "amount", "Place a bid" };
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { Integer.class, String.class, String.class,
			String.class, JButton.class };

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return 4;
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

		if (col > 3) {

			String data = "true";
			setValueAt(data, row, col);
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

		BidGUI bidGUI = new BidGUI();
		bidGUI.showGui();

	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		/* Adding components */
		switch (columnIndex) {
		case 0:
			return rowIndex;
		case 1:
			return "Text for " + rowIndex;
		case 2: // fall through
			return "Text for " + rowIndex;
		case 3: // fall through
			return "Text for " + rowIndex;
		case 4:
			final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
			/*
			 * button.addActionListener(new ActionListener() { public void
			 * actionPerformed(ActionEvent ev) { BidsListGUI bidsListGUI=new BidsListGUI();
			 * bidsListGUI.showGui(); } });
			 */
			return button;
		default:
			return "Error";
		}
	}

}
