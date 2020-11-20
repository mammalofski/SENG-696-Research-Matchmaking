package matchmaking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import matchmaking.orm.*;

public class ORM {

	private Connection conn = null;

	public ORM() {
		conn = DataBase.getConnection();
	}

//	public Number[] serialize(Class<?> cls, ResultSet querySet) {
//		Number[] objects = null;
//		while (querySet.next()) {
//			// read the result set
//			cls object = new cls();
//			System.out.println("name = " + querySet.getString("name"));
//			System.out.println("id = " + querySet.getInt("id"));
//		}
//		return null;
//	}

	public ArrayList<User> serializeUser () {
		System.out.println("in serializeUser");
		User tempUser;
		ArrayList<User> users = new ArrayList<User>();
		try {
			System.out.println("in serializeUser try");
			Statement statement = conn.createStatement();
			System.out.println("in serializeUser after statement");
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
			ResultSet qs = statement.executeQuery("select * from user");
			System.out.println("in serializeUser after query");
			while (qs.next()) {
				tempUser = new User(qs.getInt("userId"), "", 0, "", "", "", false, 0, 0, "", "", "", "");
				users.add(tempUser);
				System.out.println("the user's id is : " + tempUser.getId());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in serializeUser in catch");
			e.printStackTrace();
		}
		return users;
		
	}

}
