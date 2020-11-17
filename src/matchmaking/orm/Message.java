package matchmaking.orm;

import java.util.Date;

public class Message implements java.io.Serializable {

	private int chatroomId;
	private int recieverId;
	private int senderId;
	private int id;
	private String body;
	private Date date;
	private String recieved;
	private String seen;

	public int getId() {
		return id;
	}

	public int getRecieverId() {
		return recieverId;
	}

	public String getBody() {
		return body;
	}

	public Date getDate() {
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

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public void setDate(Date date) {
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
