import java.sql.*;


public class DBConnect {
	public static Connection connection;
	public static ResultSet rs;
	public static Statement st;
	private final  boolean local = true;
	
	String databasePrefix;
	String ID;
	String hostName;
	String databaseURL;
	String password;
	
	public DBConnect() {
		DBSettings(local);
	}
	
	public DBConnect(String host, String database, String user, String pass) {
		databasePrefix=database;
		ID=user;
		hostName=host;
		databaseURL="jdbc:mysql://" + hostName + "/" + databasePrefix + "?autoReconnect=true&useSSL=false";
		password=pass;
	}
	
	
	public boolean connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(databaseURL, ID, password);
			DBConnect.st = connection.createStatement();
		}
		catch (ClassNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
		catch (SQLException e) {
            //e.printStackTrace();
			return false;
        }
		return true;
	}
	
	private void DBSettings(boolean local) {
		if (local) {
			//EDIT SETTINGS HERE TO RUN FROM Main.java
			databasePrefix="recipe_finder";
			ID="root";
			hostName="localhost";
			databaseURL="jdbc:mysql://" + hostName + "/" + databasePrefix + "?autoReconnect=true&useSSL=false";
			password="guest";
		}
		else {
			databasePrefix="anelson_RecipeFinder";
			ID="anelson_user";
			hostName="anelson.heliohost.org";
			databaseURL="jdbc:mysql://" + hostName + "/" + databasePrefix + "?autoReconnect=true&useSSL=false";
			password="CS564";
		}
	}
}
