package matchmaking.agents.Matchmaker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import matchmaking.orm.*;
//import jade.util.leap.ArrayList;
import java.util.*;

public class BiddingSystem {
	private Connection conn;

	BiddingSystem(Connection conn1) {
		conn = conn1;
	}
	
	public void placeBid(int clientId, int providerId, int biddingAmount) {
		System.out.println("placing a bid");
		try (Statement statement = conn.createStatement()) {
			System.out.println("creating bid");
			String query = "insert into bid (clientId, providerId, amount, accepted) values(" + clientId + ", " + providerId + ", " + biddingAmount + ", 0)";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Bid> getBiddings(int userId) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("getting biddings from db");
			String query;
			ResultSet rs;
			query = "select userType from user where userId=" + userId;
			rs = statement.executeQuery(query);
			int userType = rs.getInt("userType");
			rs.close();
			if (userType == Constants.UserTypes.PROVIDER) {
				query = "select * from bid where providerId=" + userId + " and accepted<>2";
			} else {
				query = "select * from bid where clientId=" + userId;
			}
			
			System.out.println("query is " + query + " THE userId is " + userId);
			rs = statement.executeQuery(query);
			System.out.println("after query");
			ArrayList<Bid> biddings = Bid.serializeBids(rs);
			return biddings;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void rejectBid(int bidId) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("rejecting bid");
			String query = "update bid set accepted=2 where bidId=" + bidId;
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptBid(int bidId) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("accepting bid");
			String query = "update bid set accepted=1 where bidId=" + bidId;
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
