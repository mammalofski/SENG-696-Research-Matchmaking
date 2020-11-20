package matchmaking.orm;

import java.util.Date;

public class SystemContract implements java.io.Serializable {

	private int providerId;
	private int trackingId;
	private int systemContractId;
	private String name;
	private String description;
	private Date date;
	private Boolean signed;

	public int getProviderId() {
		return providerId;
	}

	public int getId() {
		return systemContractId;
	}

	public int getTrackingId() {
		return trackingId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public Boolean getSigned() {
		return signed;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}


	public void setTrackingId(int trackingId) {
		this.trackingId = trackingId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSigned(Boolean signed) {
		this.signed = signed;
	}
}
