package crimebuster.dal;

import crimebuster.model.Person;
import crimebuster.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the underlying Users table in your MySQL
 * instance. This is used to store {@link UsersDao} into your MySQL instance and retrieve {@link
 * UsersDao} from MySQL instance.
 */
public class UsersDao extends PersonDao {

  // Single pattern: instantiation is limited to one object.
  private static UsersDao instance = null;

  protected UsersDao() {
    super();
  }

  public static UsersDao getInstance() {
    if (instance == null) {
      instance = new UsersDao();
    }
    return instance;
  }

  /**
   * Create an Users instance. This runs a CREATE statement.
   */
  public Users create(Users user) throws SQLException {
    // Insert into the superclass table first.
    create(
        new Person(user.getUserName(), user.getFirstName(), user.getLastName(), user.getPassword(),
            user.getEmail(), user.getPhone()));
    String insertUser = "INSERT INTO Users(UserName,RegisteredAt) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertUser);
      insertStmt.setString(1, user.getUserName());
      insertStmt.setTimestamp(2, new Timestamp(user.getRegisteredAt().getTime()));
      insertStmt.executeUpdate();
      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  /**
   * Update the profile of the Users instance. This runs a UPDATE statement.
   */
  public Users updateByUserName(Users user, String newFirstName, String newLastName,
      String newPassWord, String newEmail, String newPhone) throws SQLException {
    // The field to update only exists in the superclass table, so we can
    // just call the superclass method.
    super.updateByUserName(user, newFirstName, newLastName, newPassWord, newEmail, newPhone);
    return user;
  }

  /**
   * Delete the Users instance. This runs a DELETE statement.
   */
  public Users delete(Users user) throws SQLException {
    String deleteUser = "DELETE FROM Users WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteUser);
      deleteStmt.setString(1, user.getUserName());
      int affectedRows = deleteStmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("No records available to delete for UserName=" + user.getUserName());
      }
      // Then also delete from the superclass.
      super.delete(user);
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

  public Users getUserFromUserName(String userName) throws SQLException {
    // To build an Users object, we need the Person record, too.
    String selectUser =
        "SELECT Users.UserName AS UserName,FirstName,LastName,PassWord,Email,Phone,RegisteredAt"
            + "FROM Users INNER JOIN Person ON Users.UserName=Person.UserName "
            + "WHERE Users.UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      if (results.next()) {
        String resultUserName = results.getString("UserName");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        Date registeredAt = new Date(results.getTimestamp("RegisteredAt").getTime());
        Users user = new Users(resultUserName, firstName, lastName, password, email, phone,
            registeredAt);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Users> getUsersFromFirstName(String firstName) throws SQLException {
    List<Users> users = new ArrayList<>();
    String selectUsers =
        "SELECT Users.UserName AS UserName,FirstName,LastName,PassWord,Email,Phone,RegisteredAt "
            + "FROM Users INNER JOIN Person ON Users.UserName=Person.UserName "
            + "WHERE Person.FirstName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUsers);
      selectStmt.setString(1, firstName);
      results = selectStmt.executeQuery();
      while (results.next()) {
        String userName = results.getString("UserName");
        String resultFirstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        Date registeredAt = new Date(results.getTimestamp("RegisteredAt").getTime());
        Users user = new Users(userName, resultFirstName, lastName, password, email, phone,
            registeredAt);
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return users;
  }
}