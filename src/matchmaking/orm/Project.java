package matchmaking.orm;

public class Project implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int bidId;
	private int id;
	private String name;
	private String description;
	private int progress;
	private int state;

	public int getProjectId() {
		return projectId;
	}

	public int getId() {
		return id;
	}

	public int getClientId() {
		return clientId;
	}

	public int getBidId() {
		return bidId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setBidId(int bidId) {
		this.bidId= bidId;
	}
	public void setId(int id) {
		this.id= id;
	}
	public void setClientId(int clientId) {
		this.clientId= clientId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.name = description;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}

