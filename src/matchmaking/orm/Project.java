package matchmaking.orm;

public class Project implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int providerId; //?
	private String name;
	private String description;
	private int progress;
	private int state;

	public int getId() {
		return projectId;
	}

	public int getClientId() {
		return clientId;
	}

	public int getProviderIdId() {
		return providerId;
	}


	public void setProviderId(int providerId) {
		this.providerId= providerId;
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

