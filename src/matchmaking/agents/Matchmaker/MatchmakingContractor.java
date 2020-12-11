package matchmaking.agents.Matchmaker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import matchmaking.orm.Bid;
import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.Project;

public class MatchmakingContractor {
	private Connection conn;
	private ResultSet rs;
	MatchmakingContract contract;

	MatchmakingContractor(Connection conn1) {
		conn = conn1;
	}

	public MatchmakingContract createContract(int bidId) {
		System.out.println("creating contract in matchmakingCotractor");
		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery("select * from bid where bidId=" + bidId);
			Bid bid;
			if (rs.next()) {
				bid = new Bid(rs.getInt("bidId"), rs.getInt("projectId"), rs.getInt("clientId"),
						rs.getInt("providerId"), rs.getInt("amount"), rs.getInt("accepted"));
			} else {
				// no bid exists
				return null;
			}

			// create the contract
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			String query = "insert into matchmakingContract (clientId, providerId, amount, date, acceptedByClient, acceptedByProvider) values(";
			query += bid.getClientId() + ", ";
			query += bid.getProviderId() + ", ";
			query += bid.getAmount() + ", ";
			query += bid.getClientId() + ", ";
			query += "0, 0)";
			statement.executeUpdate(query);

			// get the contract
//			ResultSet rs2 = statement.getGeneratedKeys();
			rs = statement.getGeneratedKeys();
			int contractId = rs.getInt(1);
			query = "select * from matchmakingContract where matchmakingContractId=" + contractId;
			rs = statement.executeQuery(query);
			if (rs.next()) {
				contract = new MatchmakingContract(contractId, rs.getInt("projectId"), rs.getInt("clientId"),
						rs.getInt("providerId"), rs.getInt("amount"), rs.getString("date"),
						rs.getInt("acceptedByClient"), rs.getInt("acceptedByProvider"));
				System.out.println("after getting the id");
			}

			// add provider and client names to contract
			query = "SELECT matchmakingContract.matchmakingContractId, j1.name AS PNAME, j2.name as CNAME "
					+ "FROM matchmakingContract " + "LEFT JOIN user j1 ON j1.userId=matchmakingContract.providerId "
					+ "LEFT JOIN user j2 ON j2.userId=matchmakingContract.clientId where matchmakingContract.matchmakingContractId="
					+ contractId;
			rs = statement.executeQuery(query);
			if (rs.next()) {
//				contract = new MatchmakingContract(rs.getInt("matchmakingContract.matchmakingContractId"), rs.getInt("matchmakingContract.projectId"), 
//						rs.getInt("matchmakingContract.clientId"), rs.getInt("providerIdmatchmakingContract."), rs.getInt("matchmakingContract.amount"), 
//						rs.getString("matchmakingContract.date"), rs.getInt("matchmakingContract.acceptedByClient"), rs.getInt("matchmakingContract.acceptedByProvider"));
				contract.setProviderName(rs.getString("PNAME"));
				contract.setClientName(rs.getString("CNAME"));
			}
			return contract;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	public ArrayList<MatchmakingContract> getContracts(int userId) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("getting biddings from db");

			String query = "SELECT matchmakingContract.matchmakingContractId, matchmakingContract.clientId, matchmakingContract.providerId, matchmakingContract.date, matchmakingContract.amount, matchmakingContract.acceptedByProvider, matchmakingContract.acceptedByClient,"
					+ "j1.name AS PNAME, j2.name as CNAME " + "FROM matchmakingContract "
					+ "LEFT JOIN user j1 ON j1.userId=matchmakingContract.providerId "
					+ "LEFT JOIN user j2 ON j2.userId=matchmakingContract.clientId "
					+ "where matchmakingContract.providerId=" + userId + " OR matchmakingContract.clientId=" + userId;

			ResultSet rs = statement.executeQuery(query);
			ArrayList<MatchmakingContract> contracts = MatchmakingContract.serializeContracts(rs);

			return contracts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public void rejectContract(int contractId, String rejector) {
		try (Statement statement = conn.createStatement()) {
			System.out.println("rejecting contract");
			String updatingAttr = rejector == "provider" ? "acceptedByProvider" : "acceptedByClient";
			String query = "update matchmakingContract set " + updatingAttr + "=2 where matchmakingContractId=" + contractId;
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptContract(int contractId, String acceptor) {
		System.out.println("in acceptContract the imputs are: " + contractId + " " + acceptor);
		try (Statement statement = conn.createStatement()) {
			System.out.println("accepting contract");
			String updatingAttr = acceptor.equals("provider") ? "acceptedByProvider" : "acceptedByClient";
			String query = "update matchmakingContract set " + updatingAttr + "=1 where matchmakingContractId=" + contractId;
			System.out.println("in acceptContract query is" + query );
			statement.executeUpdate(query);
			statement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MatchmakingContract contractAcceptedByBothUsers(int contractId) {
		System.out.println("in contractAcceptedByBothUsers");
		try (Statement statement = conn.createStatement()) {
			String query = "SELECT matchmakingContract.matchmakingContractId, matchmakingContract.clientId, matchmakingContract.providerId, matchmakingContract.date, matchmakingContract.amount, matchmakingContract.acceptedByProvider, matchmakingContract.acceptedByClient,"
					+ "j1.name AS PNAME, j2.name as CNAME " + "FROM matchmakingContract "
					+ "LEFT JOIN user j1 ON j1.userId=matchmakingContract.providerId "
					+ "LEFT JOIN user j2 ON j2.userId=matchmakingContract.clientId "
					+ "where acceptedByProvider=1 and acceptedByClient=1 and matchmakingContractId=" + contractId;
//			String query = "select * from matchmakingContract where matchmakingContractId=" + contractId + " and acceptedByProvider=1 and acceptedByClient=1";
			ResultSet rs = statement.executeQuery(query);
			System.out.println("found the contract");
			ArrayList<MatchmakingContract> contracts = MatchmakingContract.serializeContracts(rs);
			if (contracts.size() == 1) {
				MatchmakingContract contract = contracts.get(0);
				statement.close();
				rs.close();
				return contract;
			}
			statement.close();
			rs.close();
			return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public MatchmakingContract updateContract(Project project, MatchmakingContract contract) {
		System.out.println("updating contract by project");
		try (Statement statement = conn.createStatement()) {
			String query = "update matchmakingContract set projectId=" + project.getId() + " where matchmakingContractId=" + contract.getId() ;
			statement.executeUpdate(query);
			contract.setProviderId(project.getId());
			return contract;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	

	
	
}
