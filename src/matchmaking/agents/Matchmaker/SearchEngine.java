package matchmaking.agents.Matchmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.sql.Connection;
	
import matchmaking.orm.Constants;
import matchmaking.orm.User;

public class SearchEngine {
	private Connection conn;

	SearchEngine(Connection conn1) {
		conn = conn1;
	}

	public ArrayList<User> searchUser(Hashtable requestBody) {
		System.out.println("in searchUser");
		Boolean validated;
		String query;
		User tempUser;
		ArrayList<User> users = new ArrayList<User>();
		try (Statement statement = conn.createStatement()) {
			String userType = (String) requestBody.get(Constants.USER);
			requestBody.remove(Constants.USER);
			query = "select * from user";
			if (!requestBody.isEmpty()) {
				query += " where ";
				String name = (String) requestBody.get("name");
				String email = (String) requestBody.get("email");
				String specialKeywords = (String) requestBody.get("specialKeywords");
				String website = (String) requestBody.get("website");
				if (name.length() != 0) {
					query += "name like '%" + name + "%' and";
				} 
				if (email.length() != 0) {
					query += "email like '%" + email + "%' and";
				} 
				if (specialKeywords.length() != 0) {
					query += "specialKeywords like '%" + specialKeywords + "%' and";
				} 
				if (website.length() != 0) {
					query += "website like '%" + website + "%'";
				}
				// remove the excess and at the end of the query
				if (query.substring(query.length() - 3, query.length()).equals("and")) {
					query = query.substring(0, query.length() - 3);
				}
			}
			System.out.println("the query is " + query);
			ResultSet qs = statement.executeQuery(query);
			users = User.serializeUsers(qs);
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
//		try {
//			Statement statement = conn.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
//			ResultSet qs = statement.executeQuery("select * from user");
//			while (qs.next()) {
//				validated = qs.getInt("validated") > 0 ? true : false;
//				tempUser = new User(qs.getInt("userId"), qs.getString("name"), qs.getInt("userType"),
//						qs.getString("email"), qs.getString("userName"), qs.getString("password"), validated,
//						qs.getInt("accountType"), qs.getInt("hourlyCompensation"), qs.getString("specialKeyword"),
//						qs.getString("logo"), qs.getString("website"), qs.getString("cv"), true);
//				users.add(tempUser);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("in serializeUser in catch");
//			e.printStackTrace();
//		}
//		return users;

	}
}
