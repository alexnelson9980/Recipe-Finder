import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FavoriteTab extends JPanel {

	/**
	 * Create the panel.
	 */
	public FavoriteTab(String ID, String query) {
		setLayout(null);
		
		RecipeList FavoriteList = new RecipeList(ID, Queries.Get_Favorite_Recipes(ID));
		FavoriteList.setBounds(0, 25, 760, 525);
		add(FavoriteList);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FavoriteList.RefreshFavorites(ID);
			}
		});
		btnRefresh.setBounds(0, 0, 97, 25);
		add(btnRefresh);
		
	}
}
