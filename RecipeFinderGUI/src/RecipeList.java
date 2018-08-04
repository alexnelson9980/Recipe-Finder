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

	
	
	public RecipeList(String ID, ResultSet rs) {
		initialize(ID);
		GetTableData(rs);
		
	}
	/**
	 * Create the panel.
	 */
	public RecipeList(String ID, String query) {
		initialize(ID);
		if (query == "") return;
		ResultSet rs = null;
		try {
		Statement st = DBConnect.connection.createStatement();
		rs = st.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e);
		}
		GetTableData(rs);
	}
	
	private void initialize(String ID) {
		table = new JTable();
		table.setCellSelectionEnabled(true);
		setViewportView(table);
		dataModel = new DefaultTableModel(
			//GetTableData(query),
			null,
			new String[] {
				"ID", "Name", "EpiCuriousRating", "App Rating", "Favorite"
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
	
	public void GetTableData(ResultSet rs){
		dataModel.setRowCount(0);
		if (rs == null) return;
		//send to db
		//set LoadedRecipeIDs to match index in table
		//make visible grid and matching array with IDs
		
		try {
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0]=rs.getInt(1);
				row[1]=rs.getString(2);
				row[2]=rs.getDouble(3);
				row[3]=rs.getDouble(4);
				int isFav=rs.getInt(5);
				if (isFav > 0) row[4] = true;
				else row[4] = false;
				dataModel.addRow(row);			
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
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
		ResultSet rs = null;
		try {
			Statement st = DBConnect.connection.createStatement();
			rs = st.executeQuery(Queries.Get_Favorite_Recipes(ID));
			} catch (SQLException e) {
				System.out.println(e);
			}
			GetTableData(rs);
		
	}

}
