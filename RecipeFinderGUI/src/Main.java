import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		DBConnect connector;
		if (args.length > 0) {
			if (args.length !=4)
				JOptionPane.showMessageDialog(null, "Need 4 inputs: hostname databasename username password", "Database Connection Error", JOptionPane.INFORMATION_MESSAGE);
			String hostname = args[0];
			String databasename = args[1];
			String username = args[2];
			String password = args[3];
			connector = new DBConnect(hostname, databasename, username, password);		
		}
		else {
		connector = new DBConnect();
		}
		if (!connector.connect()) {
			JOptionPane.showMessageDialog(null, "Error Connecting To Database", "Database Connection Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		};
		
		ApplicationWindow window = new ApplicationWindow();
		window.frmRecipeFinderLogin.setVisible(true);
	}
}
