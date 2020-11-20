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
	private int hourlyCompensation;
	private String specialKeywords;
	private String logo;
	private String website;
	private String cv;

//	public User (int userId, String name, int userType, String email, String userName, String password, 
//			Boolean validated, int accountType, int hourlyCompensation, String specialKeywords, 
//			String logo, String website, String cv) {
//		userId = userId;
//		userType = userType;
//		name = name;
//		email = email;
//		userName = userName;
//		password = password;
//		validated = validated;
//		accountType = accountType;
//		hourlyCompensation = hourlyCompensation;
//		specialKeywords = specialKeywords;
//		logo = logo;
//		website = website;
//		cv = cv;
//	}

//	public User(int int1, Object name2, int userType2, Object email2, Object userName2, Object password2,
//			Object validated2, int accountType2, int hourlyCompensation2, Object specialKeywords2, Object logo2,
//			Object website2, Object cv2) {
//		// TODO Auto-generated constructor stub
//	}


	public User(int userId1, String name2, int userType2, String email2, String userName2, String password2, Boolean validated2,
			int accountType2, int hourlyCompensation2, String specialKeywords2, String logo2, String website2,
			String cv2) {
		userId = userId1;
		userType = userType2;
		name = name2;
		email = email2;
		userName = userName2;
		password = password2;
		validated = validated2;
		accountType = accountType2;
		hourlyCompensation = hourlyCompensation2;
		specialKeywords = specialKeywords2;
		logo = logo2;
		website = website2;
		cv = cv2;
	}

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
		return hourlyCompensation;
	}

	public String getSpecialKeyword() {
		return specialKeywords;
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
		this.hourlyCompensation = hourlyCompensation;
	}

	public void setSpecialKeyword(String specialKeyboards) {
		this.specialKeywords = specialKeyboards;
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