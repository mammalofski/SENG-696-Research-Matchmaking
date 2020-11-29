package matchmaking.agents.Matchmaker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BiddingSystem {
	private Connection conn;

	BiddingSystem(Connection conn1) {
		conn = conn1;
	}
	
	public void placeBid(int userId, int clientId, int biddingAmount) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("creating bid");
			String query = "insert into bid (clientId, providerId, amount) values(" + clientId + ", " + userId + ", " + biddingAmount + ")";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
