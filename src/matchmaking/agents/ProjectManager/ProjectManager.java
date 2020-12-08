package matchmaking.agents.ProjectManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.Project;

public class ProjectManager {
	
	Connection conn;

	public ProjectManager(Connection conn1) {
		conn = conn1;
	}
	
	public Project createProject(MatchmakingContract contract) {
		try {
			System.out.println("in createProject");
			Statement statement = conn.createStatement();
			String query = "insert into project (clientId, providerId, name, progress, state) values (";
			query += contract.getClientId() + ", ";
			query += contract.getProviderId() + ", ";
			query += "'Project between " + contract.getProviderName() + " and " + contract.getClientName() + "', ";
			query += 0 + ", ";
			query += 1 + ")";
			statement.executeUpdate(query);
			
			System.out.println("project created");
			
			ResultSet rs = statement.getGeneratedKeys();
			int projectId = rs.getInt(1);
			
			query = "select * from project where projectId=" + projectId;
			rs = statement.executeQuery(query);
			Project project = Project.serializePoject(rs);
			return project;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
