import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow {

	JFrame frmRecipeFinder;

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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 760, 550);
		frmRecipeFinder.getContentPane().add(tabbedPane);
		
		JPanel SearchPanel = new RecipeSearchPanel();
		tabbedPane.addTab("Recipe Search", null, SearchPanel, null);
		
		JPanel FavoritePanel = new JPanel();
		tabbedPane.addTab("My Favorites", null, FavoritePanel, null);
		
		JPanel ProfilePanel = new ProfilePage(ID);
		tabbedPane.addTab("Profile", null, ProfilePanel, null);
	}
}
