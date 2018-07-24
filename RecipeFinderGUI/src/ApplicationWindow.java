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
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class ApplicationWindow {

	private JFrame frmRecipeFinderLogin;
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
		if (PasswordField.getText().isEmpty()) {
			LogInButton.setEnabled(false);
			CreateUserButton.setEnabled(false);
			return;
		}
		LogInButton.setEnabled(true);
		CreateUserButton.setEnabled(true);
	}
	
	private void TryLogin(String ID, String password) {
		MessageBox.setText("failed login "+ID+" "+password);
		if (ID.equalsIgnoreCase("ABC")) {
			OpenRecipeSearch(ID);
		}
	}
	
	private void OpenRecipeSearch(String ID) {
		MainWindow newMain = new MainWindow(ID);
		newMain.frmRecipeFinder.setVisible(true);
		
	}
	
	private void TryCreateUser(String ID, String password) {
		MessageBox.setText("attempted create user "+ID+" "+password);
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
				TryLogin(UserIDField.getText(),PasswordField.getText());
			}
		});
		LogInButton.setEnabled(false);
		LogInButton.setBounds(70, 172, 112, 23);
		frmRecipeFinderLogin.getContentPane().add(LogInButton);
		
		CreateUserButton = new JButton("Create User");
		CreateUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TryCreateUser(UserIDField.getText(),PasswordField.getText());
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
