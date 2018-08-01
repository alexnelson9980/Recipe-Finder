import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		DBConnect connector = new DBConnect();
		if (!connector.connect()) {
			JOptionPane.showMessageDialog(null, "Error Connecting To Database", "Database Connection Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		};
		
		ApplicationWindow window = new ApplicationWindow();
		window.frmRecipeFinderLogin.setVisible(true);
	}
}
