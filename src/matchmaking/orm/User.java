package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;

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
	private Boolean connectedToDB;
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

	public User(String name2, int userType2, String email2, String userName2, String password2, Boolean validated2,
			int accountType2, int hourlyCompensation2, String specialKeywords2, String logo2, String website2,
			String cv2) {
		userId = 0;
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
		connectedToDB = false;
	}

	public User(int userId1, String name2, int userType2, String email2, String userName2, String password2,
			Boolean validated2, int accountType2, int hourlyCompensation2, String specialKeywords2, String logo2,
			String website2, String cv2, Boolean connectedToDB1) {
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
		connectedToDB = connectedToDB1;
	}

	public ArrayList<User> serializeUser() {
		System.out.println("in serializeUser");
		Connection conn = DataBase.getConnection();
		Boolean validated;
		User tempUser;
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			ResultSet qs = statement.executeQuery("select * from user");
			while (qs.next()) {
				validated = qs.getInt("validated") > 0 ? true : false;
				tempUser = new User(qs.getInt("userId"), qs.getString("name"), qs.getInt("userType"),
						qs.getString("email"), qs.getString("userName"), qs.getString("password"), validated,
						qs.getInt("accountType"), qs.getInt("hourlyCompensation"), qs.getString("specialKeyword"),
						qs.getString("logo"), qs.getString("website"), qs.getString("cv"), true);
				users.add(tempUser);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in serializeUser in catch");
			e.printStackTrace();
		}
		return users;

	}

	public void createUser() {
		System.out.println("in creating user");
		Connection conn = DataBase.getConnection();
		try (Statement statement = conn.createStatement()) {
			String query = "insert into user ";
			query += "(userType, name, email, userName, password, validated, accountType, hourlyCompensation, specialKeyword, logo, website, cv)";
			query += " values ('" + userType + "', '" + name + "', '" + email + "', '" + userName + "', '" + password
					+ "', '" + validated + "', '" + accountType + "', '" + hourlyCompensation + "', '" + specialKeywords
					+ "', '" + logo + "', '" + website + "', '" + cv + "')";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<User> serializeUsers(ResultSet qs) {
		System.out.println("in serializeUser");
		Boolean validated;
		User tempUser;
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (qs.next()) {
				validated = qs.getInt("validated") > 0 ? true : false;
				tempUser = new User(qs.getInt("userId"), qs.getString("name"), qs.getInt("userType"),
						qs.getString("email"), qs.getString("userName"), qs.getString("password"), validated,
						qs.getInt("accountType"), qs.getInt("hourlyCompensation"), qs.getString("specialKeyword"),
						qs.getString("logo"), qs.getString("website"), qs.getString("cv"), true);
				users.add(tempUser);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in serializeUser in catch");
			e.printStackTrace();
		}
		return users;
	}

	public void updateProfile() {
		Connection conn = DataBase.getConnection();
		try (Statement statement = conn.createStatement()) {
			String query = "update user set ";
			query += "name='" + name + "', ";
			query += "email='" + email + "', ";
			query += "hourlyCompensation=" + hourlyCompensation + ", ";
			query += "specialKeyword='" + specialKeywords + "', ";
			query += "website='" + website + "', ";
			query += "accountType=" + accountType + " ";
			query += "where userId=" + userId;
			statement.executeUpdate(query);
			System.out.println("updated user: " + name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void validate() {
		this.validated = true;
		Connection conn = DataBase.createConnection();
		try (Statement statement = conn.createStatement()) {
			statement.executeUpdate("update user set validated=1 where userId=" + userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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