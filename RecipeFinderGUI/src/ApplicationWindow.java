import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class ApplicationWindow {

	public JFrame frmRecipeFinderLogin;
	private JTextField UserIDField;
	private JPasswordField PasswordField;
	private JButton LogInButton;
	private JButton CreateUserButton;
	private JTextArea MessageBox;
	public static ApplicationWindow window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ApplicationWindow();
					window.frmRecipeFinderLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}
	
	private void EnableButtons() {
		if (UserIDField.getText().isEmpty()) {
			LogInButton.setEnabled(false);
			CreateUserButton.setEnabled(false);
			return;
		}
		if (PasswordField.getPassword().length<1) {
			LogInButton.setEnabled(false);
			CreateUserButton.setEnabled(false);
			return;
		}
		LogInButton.setEnabled(true);
		CreateUserButton.setEnabled(true);
	}
	
	private void TryLogin(String ID, String password) {
		//db query
		
		try {
			Statement st = DBConnect.connection.createStatement();
			ResultSet rs = st.executeQuery(Queries.Login(ID, password));
			if (rs.next()) {
				OpenRecipeSearch(ID);
				frmRecipeFinderLogin.dispose();		
			}
			else {
				MessageBox.setText("Invalid User ID or Password");
				return;
			}
		}
		catch (SQLException e) {
			System.out.println("Error: " + e);
			return;
		}
		
	}
	
	private void OpenRecipeSearch(String ID) {
		MainWindow newMain = new MainWindow(ID);
		newMain.frmRecipeFinder.setVisible(true);		
	}
	
	private void TryCreateUser(String ID, String password) {
		/*
		try {
			DBConnect.st.executeUpdate(Queries.Create_User(ID, "", password));
		}
		catch (SQLIntegrityConstraintViolationException e) {
			MessageBox.setText("User ID taken");
			return;
		}
		catch (SQLException e) {
			System.out.println("Error: " + e);
			return;
		}*/
		if (Queries.Update_User(ID, password, "", 0, 0)) {
			MessageBox.setText("Account created succesfully");
		}
		else {
			MessageBox.setText("User ID taken");
		}
		//db query
		//MessageBox.setText("Account created succesfully");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRecipeFinderLogin = new JFrame();
		frmRecipeFinderLogin.setTitle("Recipe Finder: Login");
		frmRecipeFinderLogin.setBounds(100, 100, 450, 300);
		frmRecipeFinderLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRecipeFinderLogin.getContentPane().setLayout(null);
		
		JLabel UserIDLabel = new JLabel("User ID: ");
		UserIDLabel.setBounds(23, 42, 76, 14);
		frmRecipeFinderLogin.getContentPane().add(UserIDLabel);
		
		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setBounds(23, 86, 80, 14);
		frmRecipeFinderLogin.getContentPane().add(PasswordLabel);
		
		LogInButton = new JButton("Log In");
		LogInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TryLogin(UserIDField.getText(),new String(PasswordField.getPassword()));
			}
		});
		LogInButton.setEnabled(false);
		LogInButton.setBounds(70, 172, 112, 23);
		frmRecipeFinderLogin.getContentPane().add(LogInButton);
		
		CreateUserButton = new JButton("Create User");
		CreateUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TryCreateUser(UserIDField.getText(),new String(PasswordField.getPassword()));
			}
		});
		CreateUserButton.setEnabled(false);
		CreateUserButton.setBounds(192, 172, 114, 23);
		frmRecipeFinderLogin.getContentPane().add(CreateUserButton);
		
		UserIDField = new JTextField();
		UserIDField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				EnableButtons();
			}

		});
		UserIDField.setBounds(109, 39, 141, 20);
		frmRecipeFinderLogin.getContentPane().add(UserIDField);
		UserIDField.setColumns(10);
		
		PasswordField = new JPasswordField();
		PasswordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				EnableButtons();
			}

		});
		PasswordField.setBounds(109, 83, 141, 20);
		frmRecipeFinderLogin.getContentPane().add(PasswordField);
		PasswordField.setColumns(10);
		
		MessageBox = new JTextArea();
		MessageBox.setEditable(false);
		MessageBox.setLineWrap(true);
		MessageBox.setBackground(SystemColor.controlHighlight);
		MessageBox.setBounds(47, 111, 320, 49);
		frmRecipeFinderLogin.getContentPane().add(MessageBox);
	}
}
