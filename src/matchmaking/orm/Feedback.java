package matchmaking.orm;

import java.util.Date;

public class Feedback implements java.io.Serializable {

	private int projectId;
	private int recieverId;
	private int senderId;
	private float rate;
	private String comment;
	private Date date;
	private int feedbackId;
	
	public int getId () {
		return feedbackId;
	}

	public int getProjectId() {
		return projectId;
	}

	public int getRecieverId() {
		return recieverId;
	}

	public int getSenderId() {
		return senderId;
	}

	public float getRate() {
		return rate;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}

	public void setClientId(int senderId) {
		this.senderId = senderId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}