import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.SystemColor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class RecipeView extends JScrollPane {

	JLabel lblAppRating;
	JLabel AppRatingVal;
	JLabel lblEpRating;
	JLabel EpRatingVal;
	JSpinner MyRatingSpinner;
	JTextPane IngredientsText;
	JTextPane DescriptionText;
	JTextPane DirectionsText;
	
	/**
	 * Create the panel.
	 */
	public RecipeView(String recipeID) {
		
		JLabel lblRecipeTitle = new JLabel(recipeID);
		setColumnHeaderView(lblRecipeTitle);
		
		JPanel panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblMyRating = new JLabel("My Rating");
		lblMyRating.setBounds(10, 11, 80, 14);
		panel.add(lblMyRating);
		
		MyRatingSpinner = new JSpinner();
		MyRatingSpinner.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				UpdateUserRating(recipeID, (int) MyRatingSpinner.getValue());
			}
		});
		MyRatingSpinner.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		MyRatingSpinner.setBounds(77, 8, 45, 20);
		panel.add(MyRatingSpinner);
		
		lblAppRating = new JLabel("App Rating:");
		lblAppRating.setBounds(142, 11, 72, 14);
		panel.add(lblAppRating);
		
		AppRatingVal = new JLabel("0");
		AppRatingVal.setBounds(205, 11, 46, 14);
		panel.add(AppRatingVal);
		
		lblEpRating = new JLabel("Epicurious Rating:");
		lblEpRating.setBounds(224, 11, 120, 14);
		panel.add(lblEpRating);
		
		EpRatingVal = new JLabel("0");
		EpRatingVal.setBounds(332, 11, 46, 14);
		panel.add(EpRatingVal);
		
		IngredientsText = new JTextPane();
		IngredientsText.setEditable(false);
		IngredientsText.setBackground(SystemColor.info);
		IngredientsText.setBounds(10, 50, 195, 430);
		panel.add(IngredientsText);
		
		DescriptionText = new JTextPane();
		DescriptionText.setBounds(224, 50, 362, 161);
		panel.add(DescriptionText);
		
		DirectionsText = new JTextPane();
		DirectionsText.setBounds(225, 219, 371, 274);
		panel.add(DirectionsText);
		
		SetRecipeData(recipeID);

	}
	
	public void SetRecipeData(String recipeID) {
		//get from db
		MyRatingSpinner.setValue(3);
		AppRatingVal.setText("2");
		EpRatingVal.setText("4");
		IngredientsText.setText("2 slices of bread \n 1 tbsp peanut butter \n 1 tbsp jam");
		DescriptionText.setText("The classic all american meal.");
		DirectionsText.setText("Spread liquid ingredients evenly on bread. \n Enjoy.");
		
	}
	
	public void UpdateUserRating(String recipeID, int rating) {
		//send to db
		//update avg rating from db
	}

}
