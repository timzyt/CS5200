package crimebuster.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;

/**
 * Use ConnectionManager to connect to your database instance.
 */
public class ConnectionManager {

  // User to connect to your database instance. By default, this is "root2".
  private final String user = "root";
  // Password for the user.
  private final String password = "123456";
  // URI to your database server. If running on the same machine, then this is "localhost".
  private final String hostName = "35.233.153.184";
  // Port to your database server. By default, this iws 3307.
  private final int port = 3306;
  // Name of the MySQL schema that contains your tables.
  private final String schema = "CrimeBuster";

  /**
   * Get the connection to the database instance.
   */
  public Connection getConnection() throws SQLException {
    Connection connection = null;
    try {
//      Properties connectionProperties = new Properties();
//      connectionProperties.put("user", this.user);
//      connectionProperties.put("password", this.password);
            try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException(e);
      }
      connection = DriverManager.getConnection(
          "jdbc:mysql://" + this.hostName + ":" + this.port + "/" + this.schema, this.user, this.password);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return connection;
  }

  /**
   * Close the connection to the database instance.
   */
  public void closeConnection(Connection connection) throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }
}