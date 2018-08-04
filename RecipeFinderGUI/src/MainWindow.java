import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow {

	public JFrame frmRecipeFinder;
	public static JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(null);
					window.frmRecipeFinder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow(String ID) {
		initialize(ID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ID) {
		frmRecipeFinder = new JFrame();
		frmRecipeFinder.setTitle("Recipe Finder");
		frmRecipeFinder.setBounds(100, 100, 800, 600);
		frmRecipeFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRecipeFinder.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 760, 550);
		frmRecipeFinder.getContentPane().add(tabbedPane);
		
		JPanel SearchPanel = new RecipeSearchPanel(ID);
		tabbedPane.addTab("Recipe Search", null, SearchPanel, null);
		
		FavoriteTab FavoritePanel = new FavoriteTab(ID);
		tabbedPane.addTab("My Favorites", null, FavoritePanel, null);
		
		JPanel ProfilePanel = new ProfilePage(ID);
		tabbedPane.addTab("Profile", null, ProfilePanel, null);	
		
		RecipeList SearchResultPanel = new RecipeList(ID, "");
		tabbedPane.addTab("Search Results", SearchResultPanel);

	}
	
}
