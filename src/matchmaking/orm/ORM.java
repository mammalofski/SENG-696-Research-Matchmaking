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

//	public ArrayList<?> serialize (Class <?> cls) {
//		
//		return null;
//	}

	public ArrayList<User> serializeUser() {
		System.out.println("in serializeUser");
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
						qs.getString("logo"), qs.getString("website"), qs.getString("cv"));
				users.add(tempUser);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in serializeUser in catch");
			e.printStackTrace();
		}
		return users;

	}

}
