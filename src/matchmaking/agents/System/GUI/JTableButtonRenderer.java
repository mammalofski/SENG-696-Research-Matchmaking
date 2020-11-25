package matchmaking.agents.System.GUI;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableButtonRenderer implements TableCellRenderer {        
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	System.out.println("into teble cells button");
    	
    	System.out.println("row"+row);
    	System.out.println("column"+column);
    	JButton button = (JButton)value;
        return button;  
    }
}
