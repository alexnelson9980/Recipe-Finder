import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				UpdateUser(ID,UserNameField.getText(),passwordField.getPassword().toString());
			}
		});
		btnUpdate.setBounds(171, 234, 89, 23);
		add(btnUpdate);

		populate(ID);
	}
	
	public void populate(String ID) {
		//db query
		UserIDField.setText(ID);
		passwordField.setText("password");
		UserNameField.setText("Doug");
	}
	
	public void UpdateUser(String ID, String Name, String Password) {
		//send update to db
	}
}
