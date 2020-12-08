package matchmaking.agents.Matchmaker;

import matchmaking.orm.ORM;
import matchmaking.orm.Project;
import matchmaking.orm.User;
import matchmaking.agents.System.GUI.*;
import matchmaking.agents.System.GUI.MatchmackingAgentGUI;
import java.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.orm.Bid;
import matchmaking.orm.ChatRoom;
import matchmaking.orm.Constants;
import matchmaking.orm.DataBase;
import matchmaking.orm.MatchmakingContract;

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
	private BiddingSystem biddingSystem;
	private MatchmakingContractor matchmakingContractor;

	protected void setup() {
		System.out.println("in agent matchmaker's setup");
		conn = DataBase.getConnection();
		biddingSystem = new BiddingSystem(conn);
		searchEngine = new SearchEngine(conn);
		matchmakingContractor = new MatchmakingContractor(conn);
		// System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new SimpleBehaviour() {

			@Override
			public void action() {
				ACLMessage msg, reply;
				Hashtable<String, String> requestBody;
				int userId;
				System.out.println("Waiting to receive msg in MatchmakerAgent");

				msg = myAgent.blockingReceive();

				if (msg != null) {
					if (msg.getPerformative() == ACLMessage.REQUEST) {
						try {
							requestBody = (Hashtable) msg.getContentObject();
							System.out.println("the message is " + requestBody);
							// if user is searching in providers
							switch (msg.getConversationId()) {
							case Constants.SEARCH:
								// search between users
								ArrayList<User> users = searchEngine.searchUser(requestBody);
								System.out.println("searched users are" + users);
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(users);
								myAgent.send(reply);
								break;
							case Constants.PLACE_BID:
								userId = Integer.parseInt(requestBody.get("userId"));
								int biddingAmount = Integer.parseInt(requestBody.get("biddingAmount"));
								int clientId = Integer.parseInt(requestBody.get("clientId"));
								biddingSystem.placeBid(userId, clientId, biddingAmount);
								break;
							case Constants.GET_BIDDINGS:
								userId = Integer.parseInt(requestBody.get("userId"));
								ArrayList<Bid> biddings = biddingSystem.getBiddings(userId);
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(biddings);
								myAgent.send(reply);
								break;
							case Constants.CREATE_MATCHMAKING_CONTRACT:
								System.out.println("msg in matchmaker received and in create contract case");
								String bidId = requestBody.get("bidId");
								biddingSystem.acceptBid(Integer.parseInt(bidId));
								MatchmakingContract contract = matchmakingContractor.createContract(Integer.parseInt(bidId));
								// send reply
								System.out.println("sending contract back to systemAgent, contract's provider name is: " + contract.getProviderName());
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								System.out.println("before setContentObject");
								reply.setContentObject(contract);
								System.out.println("after setContentObject");
								myAgent.send(reply);
								System.out.println("after reply");
								break;
							case Constants.ACCEPT_CONTRACT:
								System.out.println("msg in matchmaker received and in accept contract case");
								String contractId = requestBody.get("contractId");
								String acceptor = requestBody.get("acceptor");
								matchmakingContractor.acceptContract(Integer.parseInt(contractId), acceptor);
								break;
							case Constants.REJECT_BID:
								int bidId2 = Integer.parseInt(requestBody.get("bidId"));
								biddingSystem.rejectBid(bidId2);
								break;
							case Constants.GET_CONTRACTS:
								userId = Integer.parseInt(requestBody.get("userId"));
								ArrayList<MatchmakingContract> contracts = matchmakingContractor.getContracts(userId);
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(contracts);
								myAgent.send(reply);
								break;
							case Constants.REJECT_CONTRACT:
								int contractId2 = Integer.parseInt(requestBody.get("contractId"));
								String rejector = requestBody.get("rejector");
								matchmakingContractor.rejectContract(contractId2, rejector);
								break;
							case Constants.ACCEPT_MATCHMAKING_CONTRACT:
								int contractId3 = Integer.parseInt(requestBody.get("contractId"));
								String acceptor2 = requestBody.get("acceptor");
								matchmakingContractor.acceptContract(contractId3, acceptor2);
								
								System.out.println("before contractAcceptedByBothUsers");
								MatchmakingContract contract1 = matchmakingContractor.contractAcceptedByBothUsers(contractId3);
								System.out.println("LOG: contractAcceptedByBothUsers contract " + contract1.getProjectId());
								System.out.println("after contractAcceptedByBothUsers the contract is " + contract1);
								if (contract1 != null) {
									System.out.println("before createProject");
									Project project = createProject(contract1);
									System.out.println("before creating chatroom");
									contract1 = matchmakingContractor.updateContract(project, contract1);
									ChatRoom chatRoom = createChatRoom(project);
								}
								
								break;
							}

						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}

					}
				}
			}

			private Project createProject(MatchmakingContract contract) {
				ACLMessage msg, reply;
				try {
					msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setConversationId(Constants.CREATE_PROJECT);
					msg.setContentObject(contract);
					msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
					myAgent.send(msg);
					System.out.println("sent the message to ProjectManagerAgent to create a project");

					// wait for the response
					MessageTemplate template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
							MessageTemplate.MatchConversationId(Constants.CREATE_PROJECT));
					msg = myAgent.blockingReceive(template);
					if (msg != null) { //
						System.out.println("received the project");
						Project project = (Project) msg.getContentObject();
						return project;
					}
				} catch (IOException | UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			private ChatRoom createChatRoom(Project project) {
				ACLMessage msg, reply;
				try {
					msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setConversationId(Constants.CREATE_CHATROOM);
					msg.setContentObject(project);
					msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
					myAgent.send(msg);
					System.out.println("sent the message to ProjectManagerAgent to create a chatroom");

					// wait for the response
					MessageTemplate template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
							MessageTemplate.MatchConversationId(Constants.CREATE_CHATROOM));
					msg = myAgent.blockingReceive(template);
					if (msg != null) { //
						System.out.println("received the chatroom");
						ChatRoom chatRoom = (ChatRoom) msg.getContentObject();
						return chatRoom;
					}
				} catch (IOException | UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
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