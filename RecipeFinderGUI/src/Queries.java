import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Queries {
	//User Related Queries
	
	//Stored procedure for creating, updating, and deleting user.
	//isDelete=1 for delete, isUpdate=1 for update
	public static boolean Update_User(String userId, String password, String userName, int isDelete, int isUpdate) {
		try {
			CallableStatement myCallStmt = DBConnect.connection.prepareCall("{call modifyUser(?,?,?,?,?,?)}");
			myCallStmt.setString(1, userId);
			myCallStmt.setString(2, password);
			myCallStmt.setString(3, userName);
			myCallStmt.setInt(4, isDelete);
			myCallStmt.setInt(5, isUpdate);
			myCallStmt.registerOutParameter(6, Types.INTEGER);
			
			
			myCallStmt.execute();
			if ((isDelete==1) || (isUpdate==1)) return true;
			else if (myCallStmt.getInt(6)==0) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	//Attempt login
	public static String Login(String userId, String password) {
		return "SELECT USER.User_Name FROM USER WHERE USER.User_ID = '" + userId + "' AND USER.Password = '" + password + "';";
	}
	
	//Return username and password
	public static String User_Info(String userId) {
		return "SELECT USER.User_Name, USER.Password FROM USER WHERE User_ID = '" + userId + "';"; 
	}
	
	/*
	//Create new user
	public static String Create_User(String userId, String userName, String password) {
		return "INSERT INTO USER (User_ID, User_Name, Password) VALUES ('" + userId + "', '" + userName + "', '" + password + "');";
	}
	
	//Delete user
	public static String Delete_User(String userId) {
		return "DELETE FROM USER WHERE User_ID = '" + userId + ";'";
	}
	
	//Update user account information
	public static String Update_User(String userId, String userName, String password) {
		return "UPDATE USER SET User_Name = '" + userName + "', Password = '" + password + "' WHERE User_ID = '" + userId + "';";
	}*/
	
	//Recipe Related Queries
	
	//Get ingredients matching search string
	public static String Get_Ingredients(String ingredient) {
		return "SELECT * FROM BASE_INGREDIENT WHERE Name LIKE '%" + ingredient + "%';";
	}
	
	//Get count of categories
	public static String Get_Categories_Count() {
		return "SELECT count(*) FROM CLASSIFICATION;";
	}
	//Get all categories
	public static String Get_Categories() {
		return "SELECT * FROM CLASSIFICATION;";
	}
	
	//Get user's favorite categories
	public static String Get_Favorite_Categories(String userId) {
		return "SELECT Classification_Title FROM FAVORITE_CLASSIFICATION WHERE User_ID = '" + userId + "';";
	}
	
	//Get user's favorite recipes
	public static String Get_Favorite_Recipes(String userId) {
		return "SELECT RECIPE.Recipe_ID, RECIPE.Title, RECIPE.Epicurious_Rating, RECIPE.User_Rating, 1 AS Is_Favorite "
				+ "FROM RECIPE INNER JOIN FAVORITE_RECIPE ON RECIPE.Recipe_ID = FAVORITE_RECIPE.Recipe_ID "
				+ "WHERE FAVORITE_RECIPE.User_ID =" + userId + ";";
	}
	
	//Get information for single recipe
	public static Object [] Get_Recipe_Info(int ID, String userId) {
		try {
			CallableStatement myCallStmt = DBConnect.connection.prepareCall("{call loadSingleRecipe(?,?,?,?)}");
			myCallStmt.setInt(1, ID);
			myCallStmt.setString(2, userId);
			myCallStmt.setString(3, "");
			myCallStmt.registerOutParameter(3, Types.VARCHAR);			
			myCallStmt.registerOutParameter(4,  Types.INTEGER);
			
			myCallStmt.execute();
			String classOut = ""; //myCallStmt.getString(3);
			ResultSet rs = myCallStmt.getResultSet();
			int rating = myCallStmt.getInt(4);
			return new Object[] {classOut, rs, rating};
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	//Add or remove a recipe from a user's favorite
	public static void Update_Favorite_Recipe(String userId, int recipeId, int isDel) {
		try {
			CallableStatement myCallStmt = DBConnect.connection.prepareCall("{call editFavoriteRecipe(?,?,?)}");
			myCallStmt.setString(1, userId);
			myCallStmt.setInt(2, recipeId);
			myCallStmt.setInt(3, isDel);
			
			myCallStmt.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	//Update a user's recipe score
	public static int Update_User_Recipe_Score(String userId, int recipeId, int score, int isDel) {
		try {
			CallableStatement myCallStmt = DBConnect.connection.prepareCall("{call updateRating(?,?,?,?)}");
			myCallStmt.setString(1, userId);
			myCallStmt.setInt(2, recipeId);
			myCallStmt.setInt(3, score);
			myCallStmt.setInt(4, isDel);
			
			myCallStmt.execute();
			ResultSet rs = myCallStmt.getResultSet();
			rs.next();
			rs.getInt(1);
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public static String Get_Classification_List(String userId) {
		return 
			"SELECT " +
				"Belongs_To.Classification_Title, " +
				"Count(BELONGS_TO.Recipe_ID) AS 'Num_Recipes', " +
				"CASE " +
					"WHEN Fave_Class.Classification_Title IS NOT NULL " +
						"THEN 1 ELSE 0 " +
				"END AS 'Is_Favorite' " +
			"FROM " +
			    "BELONGS_TO " +
				"LEFT OUTER JOIN FAVORITE_CLASSIFICATION Fave_Class " +
						"ON Fave_Class.Classification_Title = BELONGS_TO.Classification_Title " +
			            "AND Fave_Class.User_ID = " + userId + " " +
			"GROUP BY " +
					"BELONGS_TO.Classification_Title " +
			"ORDER BY " +
				"Is_Favorite DESC, Num_Recipes DESC";
	}
	
	public static void Edit_Favorite_Class(String userId, String classification, int isDel) {
		try {
			CallableStatement myCallStmt = DBConnect.connection.prepareCall("{call editFavoriteClass(?,?,?)}");
			myCallStmt.setString(1, userId);
			myCallStmt.setString(2, classification);
			myCallStmt.setInt(3, isDel);
			
			myCallStmt.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
