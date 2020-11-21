package matchmaking.agents.GUI;

import jade.core.AID;
import jade.util.leap.*;
import jade.util.leap.List;
import orm.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*; // Using AWT container and component classes
import java.awt.event.*;
import java.lang.reflect.*;

class UserGUI extends Frame implements ActionListener {
	private MatchmakerAgent myAgent;

	private JTextField titleField, priceField;
	private Label lblCount;
	private TextField tfName;
	private Button btnCount;

	UserGUI(MatchmakerAgent a) {

		setLayout(new FlowLayout());

		User user = new User();
		Class user_cls = user.getClass();
		Field[] fields = user_cls.getDeclaredFields();

		System.out.printf("%d fields:%n", fields.length);
		for (Field field : fields) {
			if (field.getName() != "id") {
				var name = field.getName();
				var type = field.getType();
				add(new Label(name));
				add(new TextField(name));
			}
		}

		/*
		 * lblCount = new Label("Name"); add(lblCount);
		 * 
		 * tfName = new TextField(name); tfName.setEditable(true); add(tfName);
		 * 
		 */
		
		btnCount = new Button("Count");
		add(btnCount);
		
		btnCount.addActionListener(this);

		setTitle("AddUser");
		setSize(200, 500); // "super" Frame sets its initial window size

		setVisible(true); // "super" Frame shows

	}

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}

/*
 * public static <T> T inspect(Class<T> klazz) { Field[] fields =
 * usercls.getDeclaredFields(); List list = new ArrayList();
 * System.out.printf("%d fields:%n", fields.length); for (Field field : fields)
 * { if(field.getName()!="id") { list.add(new object{
 * field.getType().getSimpleName(), field.getName() }); }
 * System.out.printf("%s %s %s%n", Modifier.toString(field.getModifiers()),
 * field.getType().getSimpleName(), field.getName() ); } return fields; } }
 */
