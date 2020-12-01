package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bid implements java.io.Serializable {

	private int bidId;
	private int projectId;
	private int clientId;
	private int providerId;
	private int amount;
	private int accepted;
	
	public Bid(int bidId1, int projectId1, int clientId1, int providerId1, int amount1, int accepted1) {
		bidId = bidId1;
		projectId = projectId1;
		clientId = clientId1;
		providerId = providerId1;
		amount = amount1;
		accepted = accepted1; // 2 reject 1 accept 0 null
	}
	
	public static ArrayList<Bid> serializeBids(ResultSet qs) {
		System.out.println("in serializing bids");
		try {
			ArrayList<Bid> biddings = new ArrayList<Bid>();
			Bid tmpBid;
			while(qs.next()) {
				tmpBid = new Bid(qs.getInt("bidId"), 0, qs.getInt("clientId"), qs.getInt("providerId"), qs.getInt("amount"), qs.getInt("accepted"));
				System.out.println("the bid is " + tmpBid);
				biddings.add(tmpBid);
			}
			System.out.println("serialized biddings from db are " + biddings);
			return biddings;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public int getProjectId() {
		return projectId;
	}
	
	public Object getAccepted() {
		return accepted;
	}

	public int getId (){
		return bidId;
	}

	public int getClientId() {
		return clientId;
	}
	
	public int getProviderId() {
		return providerId;
	}

	public float getAmount() {
		return amount;
	}
	
//	public float getDescription() {
//		return description;
//	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
	public void setClientId(int clientId) {
		this.clientId= clientId;
	}
	
	public void setProviderId(int providerId) {
		this.providerId= providerId;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}