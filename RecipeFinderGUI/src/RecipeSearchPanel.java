import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class RecipeSearchPanel extends JPanel {
	private JTextField IncludeField;
	private JTextField ExcludeField;
	private JTextPane IncludeText;
	private JTextPane ExcludeText;

	/**
	 * Create the panel.
	 */
	public RecipeSearchPanel() {
		setLayout(null);
		
		JLabel label = new JLabel("Categories");
		label.setBounds(376, 43, 164, 14);
		add(label);
		
		JLabel lblIngredientsInclude = new JLabel("Ingredients to Include");
		lblIngredientsInclude.setBounds(37, 43, 172, 14);
		add(lblIngredientsInclude);
		
		JButton AddInclude = new JButton("+");
		AddInclude.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IncludeText.setText(IncludeField.getText());
				IncludeField.setText("");
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
				ExcludeText.setText(ExcludeField.getText());
				ExcludeField.setText("");
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
				if(IncludeField.getText().isEmpty()) {
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
		CategoriesCombo.setBounds(376, 66, 184, 30);
		add(CategoriesCombo);
		
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
		
		IncludeText = new JTextPane();
		IncludeText.setEditable(false);
		IncludeText.setBounds(36, 97, 246, 111);
		add(IncludeText);
		
		ExcludeText = new JTextPane();
		ExcludeText.setEditable(false);
		ExcludeText.setBounds(37, 266, 246, 111);
		add(ExcludeText);

	}
}
