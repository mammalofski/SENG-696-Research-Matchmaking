package matchmaking.orm;

import java.util.Date;

public class MatchmakingContract implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int providerId;
	private int Id;
	private float amount;
	private String description;
	private boolean acceptedByClient;
	private boolean acceptedByProvider;
	private Date date;

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
		return Id;
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

	public Date getDate() {
		return date;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setId(int id) {
		this.Id = id;
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

	public void setDate(Date date) {
		this.date = date;
	}
}