package matchmaking.agents.ProjectManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import matchmaking.orm.ChatRoom;
import matchmaking.orm.Constants;
import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.Message;
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
					+ "from chatRoom LEFT JOIN project j1 ON chatRoom.projectId=j1.projectId where j1.providerId="
					+ userId + " OR j1.clientId=" + userId;

			ResultSet rs = statement.executeQuery(query);
			ArrayList<ChatRoom> chatRooms = ChatRoom.serializeChatRooms(rs);

			return chatRooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Message createMessage(int userId, int chatRoomId, String messageBody) {
		try {
			System.out.println("in createMessage");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Statement statement = conn.createStatement();
			String query = "insert into message (chatRoomId, senderId, body, date) values (";
			query += chatRoomId + ", ";
			query += userId + ", '";
			query += messageBody + "', '";
			query += dtf.format(now) + "')";
			statement.executeUpdate(query);
			System.out.println("message created");

			ResultSet rs = statement.getGeneratedKeys();
			int messageId = rs.getInt(1);

			query = "select * from message where messageId=" + messageId;
			rs = statement.executeQuery(query);
			if (rs.next()) {
				Message message = Message.serializeMessage(rs);
				System.out.println("serialized message successfully");
				return message;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Message> getMessages(int chatRoomId1) {

		try {
			System.out.println("in getMessages");
			Statement statement = conn.createStatement();
			String query = "SELECT message.messageId, message.body, message.date, message.senderId, message.chatRoomId, j1.name as SNAME from message "
					+ "LEFT JOIN user j1 ON message.senderId=j1.userId where chatRoomId=" + chatRoomId1;
//			String query = "select * from message where chatRoomId=" + chatRoomId1;
			ResultSet rs = statement.executeQuery(query);

			ArrayList<Message> messages = Message.serializeMessages(rs);
			System.out.println("serialized messages successfully");
			return messages;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Project> getProjects(int userId) {
		try {
			System.out.println("in getProjects");
			Statement statement = conn.createStatement();
			String query = "SELECT * from project where providerId=" + userId + " or clientId=" + userId;
			ResultSet rs = statement.executeQuery(query);

			ArrayList<Project> projects = Project.serializeProjects(rs);
			System.out.println("serialized projects successfully");
			return projects;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateProject(String newDescription, String newDeadline, int newProgress, int projectId) {

		try {
			System.out.println("in updateProject");
			Statement statement = conn.createStatement();
			String query = "update project set ";
			if (newDescription != "") { // client
				query += "newDescription='" + newDescription + "'";
			} else if (newDeadline != "") { // provider
				query += "deadline='" + newDeadline + "'";

			}

			if (newProgress > 0 && newProgress <= 100) {
				query += ", progress=" + newProgress;
				if (newProgress == 100) {
					query += ", state=" + Constants.ProjectState.DONE;
				} else {
					query += ", state=" + Constants.ProjectState.IN_PROGRESS;
				}
			}

			query += " where projectId=" + projectId;
			statement.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void approveProject(int projectId) {
		try {
			System.out.println("in approveProject");
			ResultSet rs;
			Statement statement = conn.createStatement();
			String query = "select newDescription from project where projectId=" + projectId;
			rs = statement.executeQuery(query);
			if (rs.next()) {
				String newDescription = rs.getString("newDescription");
				rs.close();
				query = "update project set description='" + newDescription + "', newDescription='' where projectId="
						+ projectId;
				statement.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
