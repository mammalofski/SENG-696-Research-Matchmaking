package matchmaking.orm;

import java.util.Date;

public class Notification implements java.io.Serializable {

	private int systemContractId;
	private int userId;
	private int bidId;
	private int matchmackingContractId;
	private String title;
	private Date date;
	private Boolean hasSeen;

	public int getSystemContractId() {
		return systemContractId;
	}

	public int getBid_Id() {
		return bidId;
	}

	public int getUserId() {
		return userId;
	}

	public int getMatchmackingContractId() {
		return matchmackingContractId;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public Boolean getHasSeen() {
		return hasSeen;
	}

	public void setSystemContractId(int systemContractId) {
		this.systemContractId = systemContractId;
	}

	public void setBidId(int bidId) {
		this.bidId = bidId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setMatchmackingContractId(int matchmackingContractId) {
		this.matchmackingContractId = matchmackingContractId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setHasSeen(Boolean hasSeen) 
	{
		this.hasSeen=hasSeen;
	}
}
