package matchmaking.orm;

public class Constants {
	public static final String SEARCH = "search";
	public static final String GUEST = "guest";
	public static final String USER = "user";
	public static final String PLACE_BID = "place_bid";
	public static final String GET_BIDDINGS = "get_biddings";
	public static final String CREATE_MATCHMAKING_CONTRACT = "create_matchmaking_contract";
	public static final String ACCEPT_CONTRACT = "accept_contract";
	public static final String REJECT_BID = "reject_bod";
	public static final String GET_CONTRACTS = "get_contracts";
	public static final String REJECT_CONTRACT = "reject_contract";
	public static final String ACCEPT_MATCHMAKING_CONTRACT = "accept_matchmaking_contract";
	public static final String CREATE_PROJECT = "create_project";
	public static final String CREATE_CHATROOM = "create_chatroom";
	public static final String GET_CHATROOMS = "get_chatrooms";
	public static final String SEND_MESSAGE = "send_message";
	public static final String GET_MESSAGES = "get_messages";
	public static final String GET_PROJECTS = "get_projects";
	public static final String UPDATE_PROJECT = "update_project";
	public static final String SUBMIT_FEEDBACK = "submit_feedback";
	public static final String GET_FEEDBACKS = "get_feedbacks";

	public static class UserTypes {
		public static final int CLIENT = 1;
		public static final int PROVIDER = 2;
	}
	
	public static class ProjectState {
		public static final int IN_PROGRESS = 1;
		public static final int DONE = 2;
	}
}
