package matchmaking.agents.System;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import matchmaking.orm.User;

import matchmaking.orm.DataBase;

public class Profiler {
	
	private Connection conn;
	
	
	public Profiler() {
		conn = DataBase.getConnection();

		
	}
	
	
	public User signIn (String username, String password) {
		try (Statement statement = conn.createStatement()) {
			User user;
			ResultSet qs = statement.executeQuery("select * from user where username='" + username + "' and password='" + password + "'");
			while (qs.next()) {
				System.out.println("found a user in sign in " + qs);
				Boolean validated = qs.getInt("validated") > 0 ? true : false;
				user = new User(qs.getInt("userId"), qs.getString("name"), qs.getInt("userType"),
						qs.getString("email"), qs.getString("userName"), qs.getString("password"), validated,
						qs.getInt("accountType"), qs.getInt("hourlyCompensation"), qs.getString("specialKeyword"),
						qs.getString("logo"), qs.getString("website"), qs.getString("cv"), true);
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void updateProfile(User user) {
		user.updateProfile();
	}

}
