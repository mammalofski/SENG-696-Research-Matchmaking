package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Feedback implements java.io.Serializable {

	private int projectId;
	private int recieverId;
	private int senderId;
	private int rate;
	private String comment;
	private String date;
	private int feedbackId;
	
	public Feedback(int feedbackId1, int projectId1, int recieverId1, int senderId1, int rate1, 
			String date1, String comment1) {
		feedbackId = feedbackId1;
		projectId = projectId1;
		recieverId = recieverId1;
		senderId = senderId1;
		date = date1;
		rate = rate1;
		comment = comment1;
	}

	public static Feedback serializeFeedback(ResultSet rs) {
		try {
			Feedback feedback = new Feedback(rs.getInt("feedbackId"), rs.getInt("projectId"), rs.getInt("recieverId"), 
					rs.getInt("senderId"), rs.getInt("rate"), rs.getString("date"),
					rs.getString("comment"));
			return feedback;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	public static ArrayList<Feedback> serializeFeedbacks(ResultSet rs) {
		try {
			ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
			Feedback feedback;
			while (rs.next()) {
				feedback = serializeFeedback(rs);
				feedbacks.add(feedback);
			}
			return feedbacks;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
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

	public int getRate() {
		return rate;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getComment() {
		return comment;
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

	public void setDate(String date) {
		this.date = date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}