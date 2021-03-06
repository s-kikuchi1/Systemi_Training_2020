
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

//import mysql_select;

public class mysql_preparedstatement {
	
	public static void main(String[] args) throws Exception {
		String driver = "com.mysql.jdbc.Driver";
       
        String url = "jdbc:mysql://localhost/practice_db";

        String user = "root";

		String password = "SystemiSota1108";
	
		Class.forName(driver);
	
		Connection connection = DriverManager.getConnection(url,
			user, password);
		connection.setAutoCommit(false);
	
		String deleteId = "1 OR id = id";
	
		delete(connection, deleteId);
	
		mysql_select.select(connection);
	
		connection.commit();
		connection.close();
	  }
	
	  public static void delete(Connection connection,
		  String deleteId) throws SQLException {
	
		String sql = "DELETE FROM authors WHERE id = ?";
	
		PreparedStatement statement = connection
			.prepareStatement(sql);
	
		statement.setObject(1, deleteId, Types.INTEGER);
	
		int updateCount = statement.executeUpdate();
	
		if (updateCount == 1) {
		  System.out.println("削除成功");
		} else {
		  System.out.println("削除失敗");
		}
	
		statement.close();
	  }
}