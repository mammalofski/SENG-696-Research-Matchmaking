
package matchmaking.agents.System.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.agents.System.SystemAgent;
import matchmaking.orm.Constants;
import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.User;

class ContractTable extends AbstractTableModel {
	private Object[][] data;
	private String[] columnNames = { "index", "counterpartName", "amount", "acceptedByCounterpart", "Accept",
			"Reject" };
	private SystemAgent systemAgent;
	private User user;
	private ACLMessage msg, reply;
	private MessageTemplate template;

	public ContractTable(Object[][] data1, SystemAgent agent, User user1) {
		data = data1;
		systemAgent = agent;
		user = user1;

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

	public void acceptContract(Object value, int row, int col) {

		try {
			String contractIdStr = Integer.toString((int) data[row][0]);
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("contractId", contractIdStr);
			String acceptor = user.getuserType() == Constants.UserTypes.CLIENT ? "client" : "provider";
			System.out.println("in acceptContract acceptor is " + acceptor);
			requestBody.put("acceptor", acceptor);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.ACCEPT_MATCHMAKING_CONTRACT);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			System.out.println("sent the message to matchmaker to reject ccontract");
			System.out.println("LOG: in acceptContract");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void rejectContract(Object value, int row, int col) {
		try {
			String contractIdStr = Integer.toString((int) data[row][0]);
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("contractId", contractIdStr);
			String rejector = user.getuserType() == Constants.UserTypes.CLIENT ? "client" : "provider";
			requestBody.put("rejector", rejector);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.REJECT_CONTRACT);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			System.out.println("sent the message to matchmaker to reject ccontract");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setValueAt(Object value, int row, int col) {
		System.out.println("value : " + value);
		
		if (data[row][4].equals(true) || data[row][5].equals(true))
			return;
		
		if (value.equals(true)) {
			if (col == 4)
				acceptContract(value, row, col);
			else if (col == 5)
				rejectContract(value, row, col);

			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		if (value.equals(false)) {
			// do nothing

//			System.out.println("edit");
//			data[row][col] = value;
//			fireTableCellUpdated(row, col);

		}
	}

}
