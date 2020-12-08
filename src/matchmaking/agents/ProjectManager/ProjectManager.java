package matchmaking.agents.ProjectManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import matchmaking.orm.ChatRoom;
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

			System.out.println("query in createProject is " + query);
			statement.executeUpdate(query);

			System.out.println("project created");

			ResultSet rs = statement.getGeneratedKeys();
			int projectId = rs.getInt(1);

			query = "select * from project where projectId=" + projectId;
			rs = statement.executeQuery(query);
			Project project = Project.serializePoject(rs);

			// close access to db to prevent further db locking error
			rs.close();
			statement.close();
			return project;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ChatRoom createChatRoom(Project project) {
		try {
			System.out.println("in createChatRoom");
			Statement statement = conn.createStatement();
			String query = "insert into chatRoom (projectId) values (";
			query += project.getId() + ")";
			statement.executeUpdate(query);

			System.out.println("chatroom created");

			ResultSet rs = statement.getGeneratedKeys();
			int chatRoomId = rs.getInt(1);

			query = "select * from chatRoom where chatRoomId=" + chatRoomId;
			rs = statement.executeQuery(query);
			if (rs.next()) {
				ChatRoom chatRoom = ChatRoom.serializeChatRoom(rs);
				System.out.println("serialized chatroom successfully");
				return chatRoom;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ChatRoom> getChatRooms(int userId) {

		try (Statement statement = conn.createStatement()) {
			System.out.println("getting chat rooms from db");

			String query = "SELECT chatRoom.chatRoomId, chatRoom.projectId, j1.name as PNAME, j1.providerId as PID, j1.clientId as CID "
					+ "from chatRoom LEFT JOIN project j1 ON chatRoom.projectId=j1.projectId where j1.providerId=" + userId + " OR j1.clientId=" + userId;

			ResultSet rs = statement.executeQuery(query);
			ArrayList<ChatRoom> chatRooms = ChatRoom.serializeChatRooms(rs);

			return chatRooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
