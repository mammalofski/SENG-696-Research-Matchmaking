package matchmaking.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import matchmaking.GUI.StartingPointGUI;


public class SystemAgent extends Agent {
	 
	
	private StartingPointGUI myGui;

	protected void setup() {
		

		myGui = new StartingPointGUI(this);
		myGui.showGui();
		
		
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
			}});
}}
