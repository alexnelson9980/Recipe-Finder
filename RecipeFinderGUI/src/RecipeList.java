import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RecipeList extends JScrollPane {
	private JTable table;
	public int[] LoadedRecipeIDs;
	private DefaultTableModel dataModel;

	/**
	 * Create the panel.
	 */
	public RecipeList(String ID, String query) {
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		setViewportView(table);
		dataModel = new DefaultTableModel(
			//GetTableData(query),
			null,
			new String[] {
				"ID", "Name", "EpiCuriousRating", "User Rating", "Favorite"
			}
		)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Double.class, Double.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		GetTableData(query);
		table.setModel(dataModel);
		table.removeColumn(table.getColumnModel().getColumn(0));
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
				 if (col==3) {
					 UpdateFavorite(row, ID);
				 }
			 }
         });
	}
	
	public void GetTableData(String query){
		dataModel.setRowCount(0);
		if (query == null) return;
		//send to db
		//set LoadedRecipeIDs to match index in table
		//make visible grid and matching array with IDs
		
		try {
			Statement st = DBConnect.connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0]=rs.getInt(1);
				row[1]=rs.getString(2);
				row[2]=rs.getDouble(3);
				row[3]=rs.getDouble(4);
				int isFav=rs.getInt(5);
				if (isFav == 1) row[4] = true;
				else row[4] = false;
				dataModel.addRow(row);			
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		//dataModel.addRow(new Object[] {1, query, 3.06, 4.2, true});
		//dataModel.addRow(new Object[] {2, "Sandwich", 2.35, 3.7, true});
		/*return new Object[][] {
			{1, query, 3.06, 4.2, true},
			{2, "Sandwich", 2.35, 3.7, true},
		};*/
	}
		
	public void RowClicked(int RowNum, String UserID)
	{
		int recipeId = (int) dataModel.getValueAt(RowNum, 0);
		String title = (String) table.getValueAt(RowNum, 0);
		//use array to get recipeID based on rownum
		//create new view
		RecipeView recView = new RecipeView(recipeId,UserID); 
		if (MainWindow.tabbedPane.getTabCount() > 4)
		{
			MainWindow.tabbedPane.remove(4);
		}
			MainWindow.tabbedPane.addTab(title, recView);
			MainWindow.tabbedPane.setSelectedIndex(4);
	}
	
	public void UpdateFavorite(int RowNum, String userId) {
		
		int recipeId = (int) dataModel.getValueAt(RowNum, 0);
		boolean val = (boolean) table.getValueAt(RowNum, 3);
		int isDel;
		if (val == false) isDel = 1;
		else isDel = 0;
		Queries.Update_Favorite_Recipe(userId, recipeId, isDel);
		//send update to db
	}
	
	public void RefreshFavorites(String ID) {
		GetTableData(Queries.Get_Favorite_Recipes(ID));
		
	}

}
