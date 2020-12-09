package matchmaking.agents.ProjectManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import matchmaking.orm.Bid;
import matchmaking.orm.ChatRoom;
import matchmaking.orm.Constants;
import matchmaking.orm.DataBase;
import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.Message;
import matchmaking.orm.Project;
import matchmaking.orm.User;

public class ProjectManagerAgent extends Agent {
	private Connection conn;
	ProjectManager projectManager;
	
	protected void setup() {
		System.out.println("in agent projectManager's setup");
		conn = DataBase.getConnection();
		projectManager = new ProjectManager(conn);
		
		//System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new SimpleBehaviour() {
			@Override
			public void action() {
				ACLMessage msg, reply;
//				Hashtable<String, String> requestBody;
				int userId;
				System.out.println("Waiting to receive msg in ProjectManagerAgent");

				msg = myAgent.blockingReceive();

				if (msg != null) {
					if (msg.getPerformative() == ACLMessage.REQUEST) {
						try {
//							requestBody = (Hashtable) msg.getContentObject();
//							System.out.println("the message is " + requestBody);
							// if user is searching in providers
							switch (msg.getConversationId()) {
							case Constants.CREATE_PROJECT:
								System.out.println("in CREATE_PROJECT case");
								MatchmakingContract contract = (MatchmakingContract) msg.getContentObject();
								System.out.println("LOG: provider id is " + contract.getProviderId());
								Project project = projectManager.createProject(contract);
								System.out.println("created everything about project");
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(project);
								myAgent.send(reply);
								// send reply
								break;
							case Constants.CREATE_CHATROOM:
								System.out.println("in CREATE_CHATROOM case");
								Project project1 = (Project) msg.getContentObject();
								ChatRoom chatRoom = projectManager.createChatRoom(project1);
								System.out.println("created everything about chatroom");
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(chatRoom);
								myAgent.send(reply);
								// send reply
								break;
								
							case Constants.GET_CHATROOMS:
								System.out.println("in GET_CHATROOMS case");
								Hashtable<String, String> requestBody = (Hashtable) msg.getContentObject();
								int userId1 = Integer.parseInt( requestBody.get("userId"));
								ArrayList<ChatRoom> chatRooms = projectManager.getChatRooms(userId1);
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(chatRooms);
								myAgent.send(reply);
								// send reply
								break;
							case Constants.SEND_MESSAGE:
								System.out.println("in SEND_MESSAGE case");
								Hashtable<String, String> requestBody2 = (Hashtable) msg.getContentObject();
								int userId2 = Integer.parseInt( requestBody2.get("userId"));
								int chatRoomId = Integer.parseInt( requestBody2.get("chatRoomId"));
								String messageBody = requestBody2.get("messageBody");
								Message message = projectManager.createMessage(userId2, chatRoomId, messageBody);
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(message);
								myAgent.send(reply);
								// send reply
								break;
							case Constants.GET_MESSAGES:
								System.out.println("in GET_MESSAGES case");
								Hashtable<String, String> requestBody3 = (Hashtable) msg.getContentObject();
								int chatRoomId1 = Integer.parseInt( requestBody3.get("chatRoomId"));
								ArrayList<Message> messages = projectManager.getMessages(chatRoomId1);
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(messages);
								myAgent.send(reply);
								// send reply
								break;
							case Constants.GET_PROJECTS:
								System.out.println("in GET_PROJECTS case");
								Hashtable<String, String> requestBody4 = (Hashtable) msg.getContentObject();
								int userId4 = Integer.parseInt( requestBody4.get("userId"));
								ArrayList<Project> projects = projectManager.getProjects(userId4);
								
								// send reply
								reply = msg.createReply();
								reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								reply.setConversationId(msg.getConversationId());
								reply.setContentObject(projects);
								myAgent.send(reply);
								// send reply
								break;
							case Constants.UPDATE_PROJECT:
								System.out.println("in UPDATE_PROJECT case");
								Hashtable<String, String> requestBody5 = (Hashtable) msg.getContentObject();
								int projectId = Integer.parseInt( requestBody5.get("projectId"));
								if (requestBody5.get("type").equals("requestForUpdate")) {
									int userType = Integer.parseInt( requestBody5.get("requester"));
									String newDescription = "", newDeadline = "";
									int newProgress = -1;
									if (userType == Constants.UserTypes.CLIENT) {
										newDescription = requestBody5.get("description");
									} else {
										newDeadline = requestBody5.get("deadline");
										newProgress = Integer.parseInt(requestBody5.get("progress"));
									}
									
									projectManager.updateProject(newDescription, newDeadline, newProgress, projectId);
								} else if (requestBody5.get("type").equals("approveUpdate")) {
									projectManager.approveProject(projectId);
								}
								
								
								break;
							}

						} catch (UnreadableException | IOException e) {
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
		//doDelete();
	}
}