import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ProfilePage extends JPanel {
	private JPasswordField passwordField;
	private JTextField UserNameField;
	private JTextField UserIDField;

	/**
	 * Create the panel.
	 */
	public ProfilePage(String ID) {
		setLayout(null);
		
		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setBounds(60, 54, 84, 14);
		add(lblUserId);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(60, 104, 84, 14);
		add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(61, 166, 83, 14);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(166, 166, 94, 17);
		add(passwordField);
		
		UserNameField = new JTextField();
		UserNameField.setBounds(166, 101, 94, 20);
		add(UserNameField);
		UserNameField.setColumns(10);
		
		UserIDField = new JTextField();
		UserIDField.setEnabled(false);
		UserIDField.setEditable(false);
		UserIDField.setBounds(166, 51, 94, 20);
		add(UserIDField);
		UserIDField.setColumns(10);
		UserIDField.setText(ID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(171, 234, 89, 23);
		add(btnUpdate);

	}
}
