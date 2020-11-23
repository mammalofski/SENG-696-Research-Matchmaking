package matchmaking.agents.Matchmaker;

import matchmaking.orm.ORM;
import matchmaking.orm.User;
import matchmaking.agents.System.GUI.*;
import matchmaking.agents.System.GUI.MatchmackingAgentGUI;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.orm.Constants;
import matchmaking.orm.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Set;
import java.util.ArrayList;

public class MatchmakerAgent extends Agent {
	private Connection conn;
	private Hashtable catalogue;
	private MatchmackingAgentGUI myGui;
	private SearchEngine searchEngine;

	protected void setup() {
		System.out.println("in agent matchmaker's setup");
		conn = DataBase.getConnection();
		searchEngine = new SearchEngine(conn);
		// System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new SimpleBehaviour() {

			@Override
			public void action() {
				ACLMessage msg, reply;
				Hashtable<String, String> requestBody;
				System.out.println("in agent matchmaker");

				msg = myAgent.blockingReceive();

				if (msg != null) {
					if (msg.getPerformative() == ACLMessage.REQUEST) {
						try {
							requestBody = (Hashtable) msg.getContentObject();
							System.out.println("the message is " + requestBody);
							// if user is searching in providers
							switch (msg.getConversationId()) {
							case Constants.SEARCH:
								ArrayList<User> users = searchEngine.searchUser(requestBody);
								System.out.println("searched users are" + users);
								// send reply
								break;
							}

						} catch (UnreadableException e) {
							e.printStackTrace();
						}

					}
				}
			}

			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				return false;
			}
		}

		);
		// Make this agent terminate
		// doDelete();
	}

	public void updateCatalogue(final String title, final String price) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				catalogue.put(10, 20);
				System.out.println(title + " inserted into catalogue. Price = " + price);
			}
		});
	}

//	private void sendMsg(String content, String conversationId, int type, Set<AID> receivers) {
//	    ACLMessage msg = new ACLMessage(type);
//	    msg.setContent(content);
//	    msg.setConversationId(conversationId);
//	    //add receivers
//	    for (AID agent: receivers) {
//	      msg.addReceiver(agent);
//	    }
//	    myAgent.send(msg);
//	  }

}