package matchmaking.orm;

public class ChatRoom implements java.io.Serializable {

	private int chatRoomId;
	private int projectId;
	

	public int getProjectId() {
		return projectId;
	}

	public int getId () {
		return chatRoomId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
}