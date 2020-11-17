package matchmaking.agents;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProjectManagerAgent extends Agent {
	protected void setup() {
		System.out.println("in agent projectManager's setup");
		//System.out.println("Hello World! My name is " + getLocalName());
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println("in agent project");
				ACLMessage msg = receive();
				if (msg != null) {
					JOptionPane.showMessageDialog(null, "Message Recived:  " + msg.getContent());
					System.out.println("receiving the message in proeject manager");
					System.out.println(msg.getContent());
				} else {
					block();
				}
			}

		}

		);
		// Make this agent terminate
		//doDelete();
	}
}