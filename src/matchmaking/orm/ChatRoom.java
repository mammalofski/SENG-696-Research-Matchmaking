package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatRoom implements java.io.Serializable {

	private int chatRoomId;
	private int projectId;
	private String projectName;
	

	public ChatRoom(int chatRoomId1, int projectId1) {
		chatRoomId = chatRoomId1;
		projectId = projectId1;
	}

	public int getProjectId() {
		return projectId;
	}

	public int getId () {
		return chatRoomId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String name) {
		this.projectName = name;
	}

	public static ChatRoom serializeChatRoom(ResultSet rs) {
		try {
//			if (rs.next()) {
				ChatRoom chatRoom = new ChatRoom(rs.getInt("chatRoomId"), rs.getInt("projectId"));
				return chatRoom;
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public static ArrayList<ChatRoom> serializeChatRooms(ResultSet rs) {
		try {
			ArrayList<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
			ChatRoom chatRoom;
			while (rs.next()) {
				chatRoom = serializeChatRoom(rs);
				chatRoom.setProjectName(rs.getString("PNAME"));
				chatRooms.add(chatRoom);
			}
			return chatRooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}