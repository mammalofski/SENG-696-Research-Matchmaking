package matchmaking.orm;

public class User implements java.io.Serializable {

	private int userId;
	private int userType;
	private String name;
	private String email;
	private String userName;
	private String password;
	private Boolean validated;
	private int accountType;
	private int hourlycompensation;
	private String specialKeyword;
	private String logo;
	private String website;
	private String cv;

	public int getId() {
		return userId;
	}

	public int getuserType() {
		return userType;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getValidate() {
		return validated;
	}

	public int getAccountType() {
		return accountType;
	}

	public int gethourlyCompensation() {
		return hourlycompensation;
	}

	public String getSpecialKeyword() {
		return specialKeyword;
	}

	public String getLogo() {
		return logo;
	}

	public String getWebsite() {
		return website;
	}

	public String getCv() {
		return cv;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setHourlyCompensation(int hourlyCompensation) {
		this.hourlycompensation = hourlycompensation;
	}

	public void setSpecialKeyword(String specialKeyboard) {
		this.specialKeyword = specialKeyboard;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

}