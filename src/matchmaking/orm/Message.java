package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Message implements java.io.Serializable {

	private int chatroomId;
	private int recieverId;
	private int senderId;
	private int messageId;
	private String body;
	private String date;
	private String recieved;
	private String seen;
	private String senderName;
	
	public Message(int messageId1, int chatroomId1, int senderId1, String body1, String date1) {
		messageId = messageId1;
		chatroomId = chatroomId1;
		senderId = senderId1;
		body = body1;
		date = date1;
		
	}

	public static Message serializeMessage(ResultSet rs) {
		try {
			Message message = new Message(rs.getInt("messageId"), rs.getInt("chatRoomId"), rs.getInt("senderId"), rs.getString("body"), rs.getString("date"));
			return message;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	public static ArrayList<Message> serializeMessages(ResultSet rs) {
		try {
			ArrayList<Message> messages = new ArrayList<Message>();
			Message message;
			while (rs.next()) {
				message = serializeMessage(rs);
				message.setSenderName(rs.getString("SNAME"));
				messages.add(message);
			}
			return messages;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setSenderName(String name) {
		this.senderName = name;
	}
	
	public String getSenderName() {
		return senderName;
	}

	public int getId() {
		return messageId;
	}

	public int getRecieverId() {
		return recieverId;
	}

	public String getBody() {
		return body;
	}

	public String getDate() {
		return date;
	}

	public String getRecieved() {
		return recieved;
	}

	public String getSeen() {
		return seen;
	}

	public void setChatroomId(int chatroomId) {
		this.chatroomId = chatroomId;
	}

	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}
	
	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setRecieved(String recieved) {
		this.recieved = recieved;
	}

	public void setSeen(String seen) {
		this.seen = seen;
	}

	

}
