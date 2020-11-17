package matchmaking.orm;

public class Bid implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int id;
	private float amount;

	public int getProjectId() {
		return projectId;
	}

	public int getId() {
		return id;
	}

	public int getClientId() {
		return clientId;
	}

	public float getAmount() {
		return amount;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setBidId(int id) {
		this.id= id;
	}
	public void setClientId(int clientId) {
		this.clientId= clientId;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}