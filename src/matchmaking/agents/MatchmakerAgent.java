package matchmaking.agents;

import matchmaking.orm.ORM;
import matchmaking.orm.User;

import java.sql.Connection;
import java.sql.DriverManager;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import matchmaking.orm.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MatchmakerAgent extends Agent {
//	Connection conn = DataBase.getConnection();

	protected void setup() {
		System.out.println("starting to connecto to db");
		ORM orm = new ORM();
		ArrayList<User> users = orm.serializeUser();
		System.out.println("here are the user: " + users);
//		try {
			
//			Statement statement = conn.createStatement();
//			System.out.println("created statement");
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
//			
			

//			statement.executeUpdate("drop table if exists person");
//			statement.executeUpdate("create table person (id integer, name string)");
//			statement.executeUpdate("insert into person values(1, 'leo')");
//			statement.executeUpdate("insert into person values(2, 'yui')");
//			System.out.println("after executing stuff");
//			ResultSet rs = statement.executeQuery("select * from person");
//			while (rs.next()) {
//				// read the result set
//				System.out.println("name = " + rs.getString("name"));
//				System.out.println("id = " + rs.getInt("id"));
//			}
//			statement.executeUpdate("drop table if exists person");
//			System.out.println("working with dg completed and table removed");
//		} catch (SQLException e) {
//			// if the error message is "out of memory",
//			// it probably means no database file is found
//			System.out.println("in catch");
//			System.out.println(e);
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				// connection close failed.
//				System.err.println(e.getMessage());
//			}
//		}

		System.out.println("in agent matchmaker's setup");
		// System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				System.out.println("in agent matchmaker");
				// TODO Auto-generated method stub
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("send");
				msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
				send(msg);
				System.out.println("sent the message in matchmaker agent");

				Connection conn = null;
				try {
					// db parameters
					String url = "jdbc:sqlite:/home/mammalofski/eclipse/SENG-696-Research-Matchmaking/sql.db";
					// create a connection to the database
					conn = DriverManager.getConnection(url);

					System.out.println("Connection to SQLite has been established.");

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}

		);
		// Make this agent terminate
		// doDelete();
	}

}