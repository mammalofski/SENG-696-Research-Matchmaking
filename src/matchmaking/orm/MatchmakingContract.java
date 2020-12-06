package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MatchmakingContract implements java.io.Serializable {
	private int matchmakingContractId;
	private int projectId;
	private int clientId;
	private int providerId;
	private float amount;
	private String description;
	private int acceptedByClient; // 0: undecided, 1:  accepted, 2: rejected
	private int acceptedByProvider;
	private String date;
	private String providerName;
	private String clientName;

	public MatchmakingContract(int matchmakingContractId1, int projectId1, int clientId1, int providerId1, int amount1, String date1, int acceptedByClient1, int acceptedByProvider1) {
		matchmakingContractId = matchmakingContractId1;
		projectId = projectId1;
		clientId = clientId1;
		providerId = providerId;
		amount = amount1;
		date = date1;
		acceptedByClient = acceptedByClient1;
		acceptedByProvider = acceptedByProvider1;
		
	}
	
	public static ArrayList<MatchmakingContract> serializeContracts(ResultSet qs) {
		System.out.println("in serializing contracts");
		try {
			ArrayList<MatchmakingContract> contracts = new ArrayList<MatchmakingContract>();
			MatchmakingContract tmpContract;
			while(qs.next()) {
				tmpContract = new MatchmakingContract(qs.getInt("matchmakingContractId"), 0, qs.getInt("clientId"), qs.getInt("providerId"),
						qs.getInt("amount"), qs.getString("date"), qs.getInt("acceptedByClient"), qs.getInt("acceptedByProvider"));
				tmpContract.setProviderName(qs.getString("PNAME"));
				tmpContract.setClientName(qs.getString("CNAME"));
				System.out.println("the contract is " + tmpContract);
				contracts.add(tmpContract);
			}
			System.out.println("serialized contracts from db are " + contracts);
		
			
			return contracts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void setProviderName (String name) {
		this.providerName = name;
	}
	
	public void setClientName (String name) {
		this.clientName = name;
	}
	
	public String getProviderName () {
		return providerName;
	}
	
	public String getClientName () {
		return clientName;
	}

	public int getProjectId() {
		return projectId;
	}

	public int getClientId() {
		return clientId;
	}

	public int getProviderId() {
		return providerId;
	}

	public int getId() {
		return matchmakingContractId;
	}

	public float getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public int getAcceptedByClient() {
		return acceptedByClient;
	}

	public int getAcceptedByProvider() {
		return acceptedByProvider;
	}

	public String getDate() {
		return date;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}


	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAcceptedByClient(int acceptedByClient) {
		this.acceptedByClient = acceptedByClient;
	}

	public void setAcceptedByProvider(int acceptedByProvider) {
		this.acceptedByProvider = acceptedByProvider;
	}

	public void setDate(String date) {
		this.date = date;
	}
}