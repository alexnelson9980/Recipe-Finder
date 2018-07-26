import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class RecipeSearchPanel extends JPanel {
	private JTextField IncludeField;
	private JTextField ExcludeField;
	private JTextPane IncludeText;
	private JTextPane ExcludeText;
	public Hashtable<Integer,String> IncludedIngredients = new Hashtable<Integer,String>();
	public int includedCount = 0;
	public Hashtable<Integer,String> ExcludedIngredients = new Hashtable<Integer,String>();
	public int excludedCount = 0;
	private JTable CatTable;
	public int[] Cats_IDs;
	public String[] Cats_Names;
	public boolean[] Cats_Fav;
	DefaultTableModel CatModel;
	
	/**
	 * Create the panel.
	 */
	public RecipeSearchPanel(String ID) {
		setLayout(null);
		
		JLabel label = new JLabel("Categories");
		label.setBounds(554, 43, 164, 14);
		add(label);
		
		JLabel lblIngredientsInclude = new JLabel("Ingredients to Include");
		lblIngredientsInclude.setBounds(37, 43, 172, 14);
		add(lblIngredientsInclude);
		
		JButton AddInclude = new JButton("+");
		AddInclude.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IncludedIngredients.put(includedCount, IncludeField.getText());
				IncludeField.setText("");
				includedCount++;
				String text = IngredientNames(IncludedIngredients);
				IncludeText.setText(text);
				AddInclude.setEnabled(false);
			}
		});
		AddInclude.setEnabled(false);
		AddInclude.setBounds(161, 63, 48, 23);
		add(AddInclude);
		
		IncludeField = new JTextField();
		IncludeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(IncludeField.getText().isEmpty()) {
					AddInclude.setEnabled(false);
				} else {
					AddInclude.setEnabled(true);
				}
			}
		});
		IncludeField.setColumns(10);
		IncludeField.setBounds(37, 66, 119, 20);
		add(IncludeField);
		
		JLabel lblIngredientExclude = new JLabel("Ingredients to Exclude");
		lblIngredientExclude.setBounds(37, 212, 172, 14);
		add(lblIngredientExclude);
		
		JButton AddExclude = new JButton("+");
		AddExclude.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ExcludedIngredients.put(excludedCount, ExcludeField.getText());
				ExcludeField.setText("");
				excludedCount++;
				String text = IngredientNames(IncludedIngredients);
				ExcludeText.setText(text);
				AddExclude.setEnabled(false);
			}
		});
		AddExclude.setEnabled(false);
		AddExclude.setBounds(161, 232, 48, 23);
		add(AddExclude);
		
		ExcludeField = new JTextField();
		ExcludeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(ExcludeField.getText().isEmpty()) {
					AddExclude.setEnabled(false);
				} else {
					AddExclude.setEnabled(true);
				}
			}
		});
		ExcludeField.setColumns(10);
		ExcludeField.setBounds(37, 235, 119, 20);
		add(ExcludeField);
		
		JComboBox CategoriesCombo = new JComboBox();
		CategoriesCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = CategoriesCombo.getSelectedIndex();
				if (index > 0) {
					AddCatToTable(index);
				}
			}
		});
		CategoriesCombo.setBounds(554, 63, 184, 30);
		add(CategoriesCombo);
		GetCategories(ID);
		LoadComboBox(CategoriesCombo);
		
		JSpinner CalorieMin = new JSpinner();
		CalorieMin.setEnabled(false);
		CalorieMin.setBounds(376, 145, 55, 20);
		add(CalorieMin);
		
		JSpinner CalorieMax = new JSpinner();
		CalorieMax.setEnabled(false);
		CalorieMax.setBounds(478, 145, 62, 20);
		add(CalorieMax);
		
		JLabel label_3 = new JLabel("To");
		label_3.setBounds(445, 148, 22, 14);
		add(label_3);
		
		JSpinner ProteinMin = new JSpinner();
		ProteinMin.setEnabled(false);
		ProteinMin.setBounds(376, 212, 55, 20);
		add(ProteinMin);
		
		JSpinner ProteinMax = new JSpinner();
		ProteinMax.setEnabled(false);
		ProteinMax.setBounds(478, 212, 62, 20);
		add(ProteinMax);
		
		JLabel label_4 = new JLabel("To");
		label_4.setBounds(445, 215, 22, 14);
		add(label_4);
		
		JSpinner FatMin = new JSpinner();
		FatMin.setEnabled(false);
		FatMin.setBounds(376, 280, 55, 20);
		add(FatMin);
		
		JSpinner FatMax = new JSpinner();
		FatMax.setEnabled(false);
		FatMax.setBounds(478, 280, 62, 20);
		add(FatMax);
		
		JLabel label_5 = new JLabel("To");
		label_5.setBounds(445, 283, 22, 14);
		add(label_5);
		
		JSpinner SodiumMin = new JSpinner();
		SodiumMin.setEnabled(false);
		SodiumMin.setBounds(376, 340, 55, 20);
		add(SodiumMin);
		
		JSpinner SodiumMax = new JSpinner();
		SodiumMax.setEnabled(false);
		SodiumMax.setBounds(478, 340, 62, 20);
		add(SodiumMax);
		
		JLabel label_6 = new JLabel("To");
		label_6.setBounds(445, 343, 22, 14);
		add(label_6);
		
		JToggleButton CalorieToggle = new JToggleButton("Limit by Calorie Range");
		CalorieToggle.setBounds(376, 107, 164, 23);
		add(CalorieToggle);
		CalorieToggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CalorieMin.setEnabled(!CalorieMin.isEnabled());
				CalorieMax.setEnabled(!CalorieMax.isEnabled());
			}
		});
		
		JToggleButton ProteinToggle = new JToggleButton("Limit by Protein Range");
		ProteinToggle.setBounds(376, 178, 164, 23);
		add(ProteinToggle);
		ProteinToggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ProteinMin.setEnabled(!ProteinMin.isEnabled());
				ProteinMax.setEnabled(!ProteinMax.isEnabled());
			}
		});
		
		JToggleButton FatToggle = new JToggleButton("Limit by Fat Range");
		FatToggle.setBounds(376, 246, 164, 23);
		add(FatToggle);
		FatToggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FatMin.setEnabled(!FatMin.isEnabled());
				FatMax.setEnabled(!FatMax.isEnabled());
			}
		});
		
		JToggleButton SodiumToggle = new JToggleButton("Limit by Sodium Range");
		SodiumToggle.setBounds(376, 306, 164, 23);
		add(SodiumToggle);
		SodiumToggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SodiumMin.setEnabled(!SodiumMin.isEnabled());
				SodiumMax.setEnabled(!SodiumMax.isEnabled());
			}
		});
		
		JButton SearchBtn = new JButton("Search");
		SearchBtn.setBounds(395, 416, 89, 23);
		add(SearchBtn);
		SearchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Dictionary<String,Integer> vals = new Hashtable<String,Integer>();
				if (CalorieMin.isEnabled()){
					vals.put("CalorieMin", (Integer) CalorieMin.getValue());
					vals.put("CalorieMax", (Integer)CalorieMax.getValue());
				}
				if (ProteinMin.isEnabled()){
					vals.put("ProteinMin", (Integer) ProteinMin.getValue());
					vals.put("ProteinMax", (Integer) ProteinMax.getValue());
				}
				if (FatMin.isEnabled()){
					vals.put("FatMin", (Integer) FatMin.getValue());
					vals.put("FatMax", (Integer) FatMax.getValue());
				}
				if (SodiumMin.isEnabled()){
					vals.put("SodiumMin", (Integer) SodiumMin.getValue());
					vals.put("SodiumMax", (Integer) SodiumMax.getValue());
				}

				String cat = (String) CategoriesCombo.getSelectedItem();
				GetSearchResults(ID,vals,IncludedIngredients,ExcludedIngredients,cat);
			}
		});
		
		IncludeText = new JTextPane();
		IncludeText.setEditable(false);
		IncludeText.setBounds(36, 97, 246, 111);
		add(IncludeText);
		
		ExcludeText = new JTextPane();
		ExcludeText.setEditable(false);
		ExcludeText.setBounds(37, 266, 246, 111);
		add(ExcludeText);
		
		CatTable = new JTable();
		//InclCatsTable = GetFavoriteCategories(ID);
		String[] columns = {"Cat","Fav?"};
		CatModel = new DefaultTableModel(
				new Object[][] {
					{null, null},
				}
				,columns) {
			boolean[] columnEditable = new boolean[] {
					false,true
			};
			public boolean isCellEditable(int row, int col) {
				if (col==1) return true;
				return false;
			}
			
			Class[] columnTypes = new Class[] {
					String.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		
		CatModel.setColumnIdentifiers(columns);
		CatTable.setModel(CatModel);
		CatTable.setBounds(554, 104, 182, 264);
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(184, 262);
		scroll.setLocation(554, 104);
		add(scroll);
		scroll.setViewportView(CatTable);
		//add(new JScrollPane(CatTable));
		GetFavoriteCategories(CatModel);
		CatTable.addMouseListener(new MouseAdapter()
        {
			 @Override
				public void mouseClicked(MouseEvent arg0) {
				 int row=CatTable.rowAtPoint(arg0.getPoint());
				 int col= CatTable.columnAtPoint(arg0.getPoint());
				 int btn = arg0.getButton();
				 if (btn==MouseEvent.BUTTON3) {
					 CatModel.removeRow(row);
					 return;
				 }
				 if (col==1) {
					 SetFavoriteCategory((String) CatModel.getValueAt(row, 0),(boolean) CatModel.getValueAt(row, 1), ID);
				 }

			 }
        });
		
	
	}
	
	
	public void GetSearchResults(String ID, Dictionary NutriVals, Dictionary Included, Dictionary Excluded, String Cat ){
		//generate query
		String query = "PB&J";
		RecipeList list = new RecipeList(ID, query);
		if (MainWindow.tabbedPane.getTabCount() > 3)
		{
			MainWindow.tabbedPane.remove(3);
		}
			MainWindow.tabbedPane.addTab("Search Results", list);
			MainWindow.tabbedPane.setSelectedIndex(3);
	}
	
	public String IngredientNames(Hashtable<Integer,String> dict) {
		String out = "";
		for (String val: dict.values()) {
			out = out+val+"   ";
		}
		return out;
	}
	
	public void GetCategories(String UserID) {
		//load from db
		//...
		Cats_Names = new String[]{null,"Lunch","Dinner","Breakfast","Dessert"};
		Cats_Fav = new boolean[] {false,true,false,false,false};
	}
	public void LoadComboBox(JComboBox combo) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(Cats_Names);
		combo.setModel(model);
	}
	
	public void AddCatToTable(int index) {
		//index in combobox matches index in name, id, and fav array
		String name = Cats_Names[index];
		boolean fav = Cats_Fav[index];
		CatModel.addRow(new Object[] {name,fav});
	}
	
	public void GetFavoriteCategories(DefaultTableModel model){
		//run GetCategories first to populate arrs
		model.removeRow(0);
		int count=0;
		for (int i=1;i<Cats_Fav.length;i++) {
			if (Cats_Fav[i]) {
				model.addRow(new Object[] {Cats_Names[i],Cats_Fav[i]});
			}
		}	
	}
	
	public void SetFavoriteCategory(String name, boolean favorite, String UserID) {
		//generate query to create / delete favorite
	}
	
}