package matchmaking.orm;

public class Bid implements java.io.Serializable {

	private int bidId;
	private int projectId;
	private int clientId;
	private float amount;

	public int getProjectId() {
		return projectId;
	}

	public int getId (){
		return bidId;
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

	
	public void setClientId(int clientId) {
		this.clientId= clientId;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}