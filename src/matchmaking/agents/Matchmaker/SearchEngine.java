package matchmaking.agents.Matchmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.sql.Connection;
	
import matchmaking.orm.Constants;
import matchmaking.orm.User;

public class SearchEngine {
	private Connection conn;

	SearchEngine(Connection conn1) {
		conn = conn1;
	}
	
	private ArrayList<User> sortUsers(ArrayList<User> rawUsers) {
		Collections.sort(rawUsers, new SortingAlgorithm());
		return rawUsers;
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
					query += "name like '%" + name + "%' and ";
				} 
				if (email.length() != 0) {
					query += "email like '%" + email + "%' and ";
				} 
				if (specialKeywords.length() != 0) {
					query += "specialKeyword like '%" + specialKeywords + "%' and ";
				} 
				if (website.length() != 0) {
					query += "website like '%" + website + "%'";
				}
				// remove the excess and at the end of the query
				if (query.substring(query.length() - 4, query.length()).equals("and ")) {
					query = query.substring(0, query.length() - 4);
				}
			}
			System.out.println("the query is " + query);
			ResultSet qs = statement.executeQuery(query);
			users = User.serializeUsers(qs);
			return sortUsers(users);
//			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
