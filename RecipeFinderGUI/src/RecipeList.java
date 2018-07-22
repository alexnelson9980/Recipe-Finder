import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RecipeList extends JScrollPane {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public RecipeList(String ID, String query) {
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		setViewportView(table);
		DefaultTableModel dataModel = new DefaultTableModel(
			GetTableData(query),
			new String[] {
				"Name", "Favorite"
			}
		)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Object.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(dataModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(272);
		
		 table.addMouseListener(new MouseAdapter()
         {
			 @Override
				public void mouseClicked(MouseEvent arg0) {
				 int row=table.rowAtPoint(arg0.getPoint());
				 int col= table.columnAtPoint(arg0.getPoint());
				 if ((arg0.getClickCount()>1)&&(col==0)) {
					 RowClicked(row,col);
				 }
			 }
         });
	}
	
	public Object[][] GetTableData(String query){
		return new Object[][] {
			{query, true},
			{"Sandwich",true},
		};
	}
		
	public void RowClicked(int RowNum, int ColNum)
	{
		String title = (String) table.getValueAt(RowNum, ColNum);
		RecipeView recView = new RecipeView(title);
		if (MainWindow.tabbedPane.getTabCount() > 3)
		{
			MainWindow.tabbedPane.remove(3);
		}
			MainWindow.tabbedPane.addTab(title, recView);
			MainWindow.tabbedPane.setSelectedIndex(3);
	}

}
