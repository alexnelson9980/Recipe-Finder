import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RecipeList extends JScrollPane {
	private JTable table;
	public int[] LoadedRecipeIDs;

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
				"Name", "Rating", "Favorite"
			}
		)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Object.class, Double.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false,true
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
					 RowClicked(row,ID);
				 }
				 if (col==1) {
					 UpdateFavorite(row);
				 }
			 }
         });
	}
	
	public Object[][] GetTableData(String query){
		//send to db
		//set LoadedRecipeIDs to match index in table
		//make visible grid and matching array with IDs
		return new Object[][] {
			{query, 3.06, true},
			{"Sandwich", 2.35, true},
		};
	}
		
	public void RowClicked(int RowNum, String UserID)
	{
		String title = (String) table.getValueAt(RowNum, 0);
		//use array to get recipeID based on rownum
		String recipeID = title;
		//create new view
		RecipeView recView = new RecipeView(recipeID,UserID); 
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
