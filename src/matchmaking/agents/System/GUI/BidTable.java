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

class BidTable extends AbstractTableModel {
	private Object[][] data;
	private String[] columnNames = { "index", "clientName", "providerName", "amount", "Accept","Reject" };
	private SystemAgent systemAgent;
	private User user;
	private ACLMessage msg, reply;
	private MessageTemplate template;
//
//	private Object[][] data = { { "Id", "clientName", "projectName", "amount", new Boolean(true), new Boolean(false) },
//			{ 1, "Mary", "Campione", new Integer(5), new Boolean(false), new Boolean(null) },
//			{ 2, "Alison", "Huml", new Integer(3), new Boolean(true) , new Boolean(null)},
//			{ 3, "Kathy", "Walrath", new Integer(2), new Boolean(false), new Boolean(null) },
//			{ 4, "Sharon", "Zakhour", new Integer(20), new Boolean(true), new Boolean(null) },
//			{ 5, "Philip", "Milne", new Integer(10), new Boolean(false), new Boolean(null) } };
	
	public BidTable(Object[][] data1, SystemAgent agent, User user1) {
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
	
	public void acceptBid(Object value, int row, int col) {
		System.out.println("edit");
		data[row][col] = value;
		fireTableCellUpdated(row, col);
		System.out.println("requesting for contract");
		MatchmakingContract contract = createContract(data[row][0]);
		System.out.println("got the contract in bidTable");
		ContractGUI contractGUI = new ContractGUI(systemAgent, user, contract);
		System.out.println("after initiating contractGUI");
//		ClientRateGUI clientRateGUI=new ClientRateGUI();
		contractGUI.showGui();
	}
	public void rejectBid(Object value, int row, int col) {
		try {
			String bidIdStr = Integer.toString((int) data[row][0]);
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("bidId", bidIdStr);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.REJECT_BID);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			System.out.println("sent the message to matchmaker to reject bid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		System.out.println("value : " + value);
		if (value.equals(true)) {
			if (col == 4)
				acceptBid(value, row, col);
			else if (col == 5)
				rejectBid(value, row, col);
			
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
	
	private MatchmakingContract createContract(Object bidId) {
		// send message to MatchmakerAgent to create a contract
		
		try {
			String bidIdStr = Integer.toString((int) bidId);
			Hashtable<String, String> requestBody = new Hashtable<String, String>();
			requestBody.put("bidId", bidIdStr);
			msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(Constants.CREATE_MATCHMAKING_CONTRACT);
			msg.setContentObject(requestBody);
			msg.addReceiver(new AID("MatchmakerAgent", AID.ISLOCALNAME));
			systemAgent.send(msg);
			System.out.println("sent the message to matchmaker to create contract");
			// wait for the response
			template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId(Constants.CREATE_MATCHMAKING_CONTRACT));
			msg = systemAgent.blockingReceive(template);
			if (msg != null) { //
				MatchmakingContract contract = (MatchmakingContract) msg.getContentObject();
				System.out.println("contract received in systemAgent ");
				return contract;
			}
		} catch (IOException | UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}

}
