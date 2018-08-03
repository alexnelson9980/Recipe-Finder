import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class IngredientLookupDialog extends JDialog {
	
	private JTable ingTable;
	private DefaultTableModel ingModel;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			IngredientLookupDialog dialog = new IngredientLookupDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	/*
	public IngredientLookupDialog() {
		//initialize();
		populateTable();
	}*/
	
	public IngredientLookupDialog(String searchString, int from, RecipeSearchPanel panel) {
		initialize(from, panel);
		populateTable(searchString);	
	}
	
	private void initialize(int from, RecipeSearchPanel panel) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		ingTable = new JTable();
		String[] IngCol = {"ID", "Ingredient"};
		ingModel = new DefaultTableModel(
				new Object[][] {
				}
				,IngCol) {
			boolean[] columnEditable = new boolean [] {
					false
			};
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
			Class[] columnTypes = new Class[] {
					int.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		
		ingModel.setColumnIdentifiers(IngCol);
		ingTable.setModel(ingModel);
		ingTable.removeColumn(ingTable.getColumnModel().getColumn(0));
		contentPanel.setLayout(null);
		
		JScrollPane scrollIng = new JScrollPane();
		scrollIng.setBounds(0, 0, 432, 213);
		contentPanel.add(scrollIng);
		scrollIng.setViewportView(ingTable);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = ingTable.getSelectedRow();
				if (row == -1)
					return;
				Object[] array = new Object[] {ingModel.getValueAt(row, 0), ingModel.getValueAt(row, 1)};
				if (from == 1)
					panel.addIncludedIngredient(array);
				else if (from == 2)
					panel.addExcludedIngredient(array);
				close();
			}
		});
		btnAccept.setBounds(5, 215, 97, 25);
		contentPanel.add(btnAccept);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnCancel.setBounds(330, 215, 97, 25);
		contentPanel.add(btnCancel);
	}
	
	private void close() {
		this.dispose();
	}
	
	private void populateTable(String searchString) {
		try {
			Statement st = DBConnect.connection.createStatement();
			ResultSet rs = st.executeQuery(Queries.Get_Ingredients(searchString));
			while (rs.next()) {
				int ID = (int)rs.getObject("Ingredient_ID");
				String name = (String)rs.getObject("Name");
				ingModel.addRow(new Object[] {ID, name});
			}
		}
		catch (SQLException e) {
			System.out.println("Error: " + e);
			return;
		}
		
	}

}
