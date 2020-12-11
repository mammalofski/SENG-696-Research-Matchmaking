package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Project implements java.io.Serializable {

	private int projectId;
	private int clientId;
	private int providerId;
	private String deadline;
	private String name;
	private String description;
	private int progress;
	private int state;
	private String newDescription = "";

	public Project(int projectId1, int clientId1, int providerId1, String name1, String description1, int progress1,
			int state1, String deadline1) {
		projectId = projectId1;
		clientId = clientId1;
		providerId = providerId1;
		name = name1;
		description = description1;
		progress = progress1;
		state = state1;
		deadline = deadline1;
	}

	public static Project serializePoject(ResultSet rs) {
		try {
			if (rs.next()) {
				System.out.println("LOG: in serializer: deadline is " + rs.getString("deadline") + "and description is " +rs.getString("description"));
				
				Project project = new Project(rs.getInt("projectId"), rs.getInt("clientId"), rs.getInt("providerId"),
						rs.getString("name"), rs.getString("description"), rs.getInt("progress"), rs.getInt("state"),
						rs.getString("deadline"));
				return project;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static ArrayList<Project> serializeProjects(ResultSet rs) {
		try {
			ArrayList<Project> projects = new ArrayList<Project>();
			Project project;
			while (rs.next()) {
				project = new Project(rs.getInt("projectId"), rs.getInt("clientId"), rs.getInt("providerId"),
						rs.getString("name"), rs.getString("description"), rs.getInt("progress"), rs.getInt("state"),
						rs.getString("deadline"));
				project.setNewDescription(rs.getString("newDescription"));
				projects.add(project);
			}
			return projects;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getId() {
		return projectId;
	}

	public String getName() {
		return name;
	}

	public int getClientId() {
		return clientId;
	}

	public int getProviderIdId() {
		return providerId;
	}

	public int getProgress() {
		return progress;
	}

	public int getState() {
		return state;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	
	public void setDeadline(String deadline1) {
		this.deadline = deadline1;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
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
	
	public void setNewDescription(String newDescription1) {
		this.newDescription= newDescription1 ;
	}

	public String getNewDescription() {
		return newDescription;
	}

	public String getDescription() {
		return description;
	}

	public String getDeadline() {
		return deadline;
	}

}
