package crimebuster.dal;

import crimebuster.model.Admin;
import crimebuster.model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the underlying Users table in your MySQL
 * instance. This is used to store {@link AdminDao} into your MySQL instance and retrieve {@link
 * AdminDao} from MySQL instance.
 */
public class AdminDao extends PersonDao {

  // Single pattern: instantiation is limited to one object.
  private static AdminDao instance = null;

  protected AdminDao() {
    super();
  }

  public static AdminDao getInstance() {
    if (instance == null) {
      instance = new AdminDao();
    }
    return instance;
  }

  /**
   * Create an Admin instance. This runs a CREATE statement.
   */
  public Admin create(Admin admin) throws SQLException {
    // Insert into the superclass table first.
    create(new Person(admin.getUserName(), admin.getFirstName(), admin.getLastName(),
        admin.getPassword(), admin.getEmail(), admin.getPhone()));
    String insertAdmin = "INSERT INTO Admin(UserName,Level) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertAdmin);
      insertStmt.setString(1, admin.getUserName());
      insertStmt.setInt(2, admin.getLevel());
      insertStmt.executeUpdate();
      return admin;
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
   * Update the profile of the Admin instance. This runs a UPDATE statement.
   */
  public Admin updateByUserName(Admin admin, String newFirstName, String newLastName,
      String newPassWord, String newEmail, String newPhone) throws SQLException {
    // The field to update only exists in the superclass table, so we can
    // just call the superclass method.
    super.updateByUserName(admin, newFirstName, newLastName, newPassWord, newEmail, newPhone);
    return admin;
  }

  /**
   * Delete the Admin instance. This runs a DELETE statement.
   */
  public Admin delete(Admin admin) throws SQLException {
    String deleteAdmin = "DELETE FROM Users WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteAdmin);
      deleteStmt.setString(1, admin.getUserName());
      int affectedRows = deleteStmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException(
            "No records available to delete for UserName=" + admin.getUserName());
      }
      // Then also delete from the superclass.
      super.delete(admin);
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

  public Admin getAdminFromUserName(String userName) throws SQLException {
    // To build an Admin object, we need the Person record, too.
    String selectAdmin = "SELECT Admin.UserName AS UserName,FirstName,LastName,PassWord,Email,Phone,Level"
        + "FROM Admin INNER JOIN Person ON Admin.UserName=Person.UserName "
        + "WHERE Users.UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectAdmin);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      if (results.next()) {
        String resultUserName = results.getString("UserName");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        int level = results.getInt("Level");
        Admin admin = new Admin(resultUserName, firstName, lastName, password, email, phone, level);
        return admin;
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

  public List<Admin> getAdminFromFirstName(String firstName) throws SQLException {
    List<Admin> admins = new ArrayList<>();
    String selectAdmins = "SELECT Admin.UserName AS UserName,FirstName,LastName,PassWord,Email,Phone,Level "
        + "FROM Admin INNER JOIN Person ON Admin.UserName=Person.UserName "
        + "WHERE Person.FirstName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectAdmins);
      selectStmt.setString(1, firstName);
      results = selectStmt.executeQuery();
      while (results.next()) {
        String userName = results.getString("UserName");
        String resultFirstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        int level = results.getInt("Level");
        Admin admin = new Admin(userName, resultFirstName, lastName, password, email, phone, level);
        admins.add(admin);
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
    return admins;
  }
}