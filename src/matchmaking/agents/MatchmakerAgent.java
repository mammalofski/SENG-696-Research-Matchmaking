package matchmaking.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MatchmakerAgent extends Agent {

	protected void setup() {
		//System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new OneShotBehaviour() {
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("send");
				msg.addReceiver(new AID("ProjectManagerAgent", AID.ISLOCALNAME));
				send(msg);
			}
		}

		);
		// Make this agent terminate
		//doDelete();
	}
	  
	}