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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecipeView extends JScrollPane {

	JLabel lblAppRating;
	JLabel lblRecipeTitle;
	JLabel AppRatingVal;
	JLabel lblEpRating;
	JLabel EpRatingVal;
	JSpinner MyRatingSpinner;
	JTextPane IngredientsText;
	JTextPane DescriptionText;
	JTextPane DirectionsText;
	JTextPane NutritionText;
	
	/**
	 * Create the panel.
	 */
	
	//TODO nutrition
	public RecipeView(int recipeID,String UserID) {
		
		lblRecipeTitle = new JLabel(); //change to title
		setColumnHeaderView(lblRecipeTitle);
		
		JPanel panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblMyRating = new JLabel("My Rating");
		lblMyRating.setBounds(10, 11, 80, 14);
		panel.add(lblMyRating);
		
		MyRatingSpinner = new JSpinner();

		MyRatingSpinner.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		MyRatingSpinner.setBounds(77, 8, 45, 20);
		panel.add(MyRatingSpinner);
		
		lblAppRating = new JLabel("App Rating:");
		lblAppRating.setBounds(212, 11, 72, 14);
		panel.add(lblAppRating);
		
		AppRatingVal = new JLabel("0");
		AppRatingVal.setBounds(285, 11, 46, 14);
		panel.add(AppRatingVal);
		
		lblEpRating = new JLabel("Epicurious Rating:");
		lblEpRating.setBounds(314, 11, 120, 14);
		panel.add(lblEpRating);
		
		EpRatingVal = new JLabel("0");
		EpRatingVal.setBounds(422, 11, 46, 14);
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
		
		NutritionText = new JTextPane();
		NutritionText.setEditable(false);
		NutritionText.setBackground(SystemColor.info);
		NutritionText.setBounds(603, 50, 152, 430);
		panel.add(NutritionText);
		
		JButton btnUpdateScore = new JButton("Update");
		btnUpdateScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateUserRating(UserID, recipeID);
			}
		});
		btnUpdateScore.setBounds(125, 8, 80, 20);
		panel.add(btnUpdateScore);
		
		SetRecipeData(recipeID,UserID);

	}
	
	public void SetRecipeData(int recipeID, String UserID) {
		
		Object[] output = Queries.Get_Recipe_Info(recipeID, UserID);
		String Classifications = (String)output[0];
		ResultSet rs = (ResultSet)output[1];
		try {
			rs.next();
			
			//Set nutrition info
			String calories = rs.getString("Calories");
			String sodium = rs.getString("Sodium");
			String fat = rs.getString("Fat");
			String protein = rs.getString("Protein");
			String text = "Calories: "+calories+"\n";
			text+="Fat: "+fat+"\n";
			text+="Protein: "+protein+"\n";
			text+="Sodium: "+sodium+"\n";
			NutritionText.setText(text);
			
			//Set rating info
			MyRatingSpinner.setValue((int)output[2]);
			Double temp = rs.getDouble("User_Rating");
			AppRatingVal.setText(temp.toString());
			temp = rs.getDouble("Epicurious_Rating");
			EpRatingVal.setText(temp.toString());
			
			IngredientsText.setText(rs.getString("Prepared_Ingredients"));
			DescriptionText.setText(rs.getString("Description"));
			DirectionsText.setText(rs.getString("Instructions"));
			

			
		} catch (SQLException e) {
			System.out.println(e);
		}
		/*
		//get from db using ID, userID
		//lblRecipeTitle.setText(title);
		MyRatingSpinner.setValue(3);
		AppRatingVal.setText("2");
		EpRatingVal.setText("4");
		IngredientsText.setText("2 slices of bread \n 1 tbsp peanut butter \n 1 tbsp jam");
		DescriptionText.setText("The classic all american meal.");
		DirectionsText.setText("Spread liquid ingredients evenly on bread. \n Enjoy.");
		//LoadNutritionText(recipeID);*/
		
	}
	
	public void LoadNutritionText(String RecipeID) {
		//query for values:
		String cals = "150";
		String fat = "8";
		String prot = "3";
		String sodium = "820";
		//format
		String text = "Calories: "+cals+"\n";
		text+="Fat: "+fat+"\n";
		text+="Protein: "+prot+"\n";
		text+="Sodium: "+sodium+"\n";
		NutritionText.setText(text);
	}
	
	public void UpdateUserRating(String UserID, int recipeID) {
		//send to db
		//update avg rating from db
		int avgRating = Queries.Update_User_Recipe_Score(UserID, recipeID, (int)MyRatingSpinner.getValue(), 0);
		AppRatingVal.setText(Integer.toString(avgRating));
	}
}
