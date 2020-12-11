
package matchmaking.agents.System.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import matchmaking.agents.ProjectManager.ProjectManagerAgent;
import matchmaking.agents.System.SystemAgent;
import matchmaking.orm.Constants;
import matchmaking.orm.MatchmakingContract;
import matchmaking.orm.Project;
import matchmaking.orm.User;

public class ProjectProgressJTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static String[] COLUMN_NAMES = new String[] { "index", "projectName", "ShowProject","RateProject" };
	private static Class<?>[] COLUMN_TYPES = new Class<?>[] { Integer.class, String.class, JButton.class, JButton.class };
	String[][] data;
	User user;
	SystemAgent myAgent;
	private  ArrayList<Project> projects;

	ProjectProgressJTable(String[][] data1, SystemAgent agent, User user1, ArrayList<Project> projects1) {
		super();
		data = data1;
		myAgent = agent;
		user = user1;
		projects = projects1;

	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public void setData(String[][] data1) {
		data = data1;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int row, int col) {

		if (col > 1) {

			String data1 = "true";
			setValueAt(data1, row, col);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Don't need to implement this method unless your table's data can change.
	 */

	public void enterProject(Project project) {
		System.out.println("enering chat room");
		
		ProjectProgressGUI projectProgressGUI = new ProjectProgressGUI(myAgent, user, project);
		projectProgressGUI.showGui();
		
	}

	public void setValueAt(String value, int row, int col) {
		if(col==2) {
			enterProject(projects.get(row));
		}else if(col==3) {
			enterRate(projects.get(row));
		}
		
		

//		data[row][col] = value;
		// do nothing

//			System.out.println("edit");
//			data[row][col] = value;
//			fireTableCellUpdated(row, col);

	}

	private void enterRate(Project project) {
		System.out.println("enering cProject");
		if(project.getState() == Constants.ProjectState.DONE) {
			FeedbackGUI feedbackGUI=new FeedbackGUI(myAgent,user,project);
			feedbackGUI.showGui();
		}
		
		
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {

		switch (columnIndex) {
		case 0:
			return rowIndex;
		case 1:
			return data[rowIndex][columnIndex];

		case 2:
			final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
			return button;
		case 3:
			final JButton button1 = new JButton(COLUMN_NAMES[columnIndex]);
			return button1;
		default:
			return "";

		}

	}

}

