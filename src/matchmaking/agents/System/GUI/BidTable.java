package matchmaking.agents.System.GUI;

import javax.swing.table.AbstractTableModel;

class BidTable extends AbstractTableModel {
	private Object[][] data;
	private String[] columnNames = { "index", "clientName", "providerName", "amount", "Accept","Reject" };
//
//	private Object[][] data = { { "Id", "clientName", "projectName", "amount", new Boolean(true), new Boolean(false) },
//			{ 1, "Mary", "Campione", new Integer(5), new Boolean(false), new Boolean(null) },
//			{ 2, "Alison", "Huml", new Integer(3), new Boolean(true) , new Boolean(null)},
//			{ 3, "Kathy", "Walrath", new Integer(2), new Boolean(false), new Boolean(null) },
//			{ 4, "Sharon", "Zakhour", new Integer(20), new Boolean(true), new Boolean(null) },
//			{ 5, "Philip", "Milne", new Integer(10), new Boolean(false), new Boolean(null) } };
	
	public BidTable(Object[][] data1) {
		data = data1;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/**
	 * JTable uses this method to determine the default renderer/ editor for each
	 * cell. If we didn't implement this method, then the last column would contain
	 * text ("true"/"false"), rather than a check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/**
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col > 3) {

			String data = getValueAt(row, col).toString();
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
		if (value.equals(true)) {
			System.out.println("edit");
			data[row][col] = value;
			fireTableCellUpdated(row, col);
			ClientRateGUI clientRateGUI=new ClientRateGUI();
			clientRateGUI.showGui();
		}
		if (value.equals(false)) {
			System.out.println("edit");
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

}
