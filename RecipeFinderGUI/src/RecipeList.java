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
					 RowClicked(row);
				 }
				 if (col==1) {
					 UpdateFavorite(row);
				 }
			 }
         });
	}
	
	public Object[][] GetTableData(String query){
		//send to db
		//make visible grid and matching array with IDs
		return new Object[][] {
			{query, true},
			{"Sandwich",true},
		};
	}
		
	public void RowClicked(int RowNum)
	{
		String title = (String) table.getValueAt(RowNum, 0);
		//use array to get recipeID
		RecipeView recView = new RecipeView(title); //this will be ID
		if (MainWindow.tabbedPane.getTabCount() > 3)
		{
			MainWindow.tabbedPane.remove(3);
		}
			MainWindow.tabbedPane.addTab(title, recView);
			MainWindow.tabbedPane.setSelectedIndex(3);
	}
	
	public void UpdateFavorite(int RowNum) {
		boolean val = (boolean) table.getValueAt(RowNum, 1);
		//send update to db
	}

}
