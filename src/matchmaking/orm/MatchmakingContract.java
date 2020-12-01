package matchmaking.orm;

import java.util.Date;

public class MatchmakingContract implements java.io.Serializable {
	private int matchmakingContractId;
	private int projectId;
	private int clientId;
	private int providerId;
	private float amount;
	private String description;
	private boolean acceptedByClient;
	private boolean acceptedByProvider;
	private String date;
	private String providerName;
	private String clientName;

	public MatchmakingContract(int matchmakingContractId1, int projectId1, int clientId1, int providerId1, int amount1, String date1, int acceptedByClient1, int acceptedByProvider1) {
		matchmakingContractId = matchmakingContractId;
		projectId = projectId1;
		clientId = clientId1;
		providerId = providerId;
		amount = amount1;
		date = date1;
		acceptedByClient = acceptedByClient1 == 1;
		acceptedByProvider = acceptedByProvider1 == 1;
		
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

	public Boolean getAcceptedByClient() {
		return acceptedByClient;
	}

	public Boolean getAcceptedByProvider() {
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

	public void setAcceptedByClient(Boolean acceptedByClient) {
		this.acceptedByClient = acceptedByClient;
	}

	public void setAcceptedByProvider(Boolean acceptedByProvider) {
		this.acceptedByProvider = acceptedByProvider;
	}

	public void setDate(String date) {
		this.date = date;
	}
}