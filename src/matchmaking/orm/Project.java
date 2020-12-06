package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Project implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int providerId;
	private String name;
	private String description;
	private int progress;
	private int state;
	
	
	public Project(int projectId1, int clientId1, int providerId1, String name1, String description1, int progress1, int state1) {
		projectId = projectId1;
		clientId = clientId1;
		providerId = providerId1;
		name = name1;
		description = description1;
		progress = projectId1;
		state = state1;
	}

	public static Project serializePoject(ResultSet rs) {
		try {
			if (rs.next()) {
				Project project = new Project(rs.getInt("projectId"), rs.getInt("clientId"), rs.getInt("providerId"), rs.getString("name"),
						rs.getString("description"), rs.getInt("progress"), rs.getInt("state"));
				return project;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

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

