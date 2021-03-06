package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    static {
        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection()
            throws SQLException {

        // 「database_name」は各環境に合わせます
        String url = "jdbc:mysql://localhost/database_name";
        // MySQLに接続する際のユーザー名(デフォルトはroot)
        String user = "root";
        // MySQLに接続する際のパスワード(今回はroot)
        String password = "root";
        Connection connection = DriverManager.getConnection(url,
                user, password);
        connection.setAutoCommit(false);

        return connection;
    }

    public List<Book> selectAll() throws SQLException {

        Connection connection = getConnection();
        String sql = "SELECT * FROM books ORDER BY id";
        PreparedStatement statement = connection
                .prepareStatement(sql);

        List<Book> ret = new ArrayList<Book>();

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthorId(rs.getInt("author_id"));
            book.setTitle(rs.getString("title"));

            ret.add(book);
        }

        rs.close();
        statement.close();
        connection.close();

        return ret;
    }

    public int insert(Book book) throws SQLException {

        Connection connection = getConnection();

        int id = 9;

        book.setId(id);

        String sql = "INSERT INTO books (id, author_id, title) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        statement.setInt(2, book.getAuthorId());
        statement.setString(3, book.getTitle());

        int updateCount = statement.executeUpdate();

        statement.close();
        connection.commit();
        connection.close();

        return updateCount;
    }
}