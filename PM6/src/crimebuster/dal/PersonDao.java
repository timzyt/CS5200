package crimebuster.dal;

import crimebuster.model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the underlying Person table in your MySQL
 * instance. This is used to store {@link Person} into your MySQL instance and retrieve {@link
 * Person} from MySQL instance.
 */
public class PersonDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static PersonDao instance = null;

  protected PersonDao() {
    connectionManager = new ConnectionManager();
  }

  public static PersonDao getInstance() {
    if (instance == null) {
      instance = new PersonDao();
    }
    return instance;
  }

  /**
   * Save the Person instance by storing it in your MySQL instance. This runs a INSERT statement.
   */
  public Person create(Person person) throws SQLException {
    String insertPerson = "INSERT INTO Person(UserName,FirstName,LastName,PassWord,Email,Phone) VALUES(?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertPerson);
      insertStmt.setString(1, person.getUserName());
      insertStmt.setString(2, person.getFirstName());
      insertStmt.setString(3, person.getLastName());
      insertStmt.setString(4, person.getPassword());
      insertStmt.setString(5, person.getEmail());
      insertStmt.setString(6, person.getPhone());
      insertStmt.executeUpdate();
      return person;
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
   * Update the profile of the Person instance. This runs a UPDATE statement.
   */
  public Person updateByUserName(Person person, String newFirstName, String newLastName,
      String newPassWord, String newEmail, String newPhone) throws SQLException {
    String updatePerson = "UPDATE Person SET FirstName=?,LastName=?,PassWord=?,Email=?,Phone=? WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updatePerson);
      updateStmt.setString(1, newFirstName);
      updateStmt.setString(2, newLastName);
      updateStmt.setString(3, newPassWord);
      updateStmt.setString(4, newEmail);
      updateStmt.setString(5, newPhone);
      updateStmt.setString(6, person.getUserName());
      updateStmt.executeUpdate();
      // Update the person param before returning to the caller.
      person.setFirstName(newFirstName);
      person.setLastName(newLastName);
      person.setPassword(newPassWord);
      person.setEmail(newEmail);
      person.setPhone(newPhone);
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  /**
   * Delete the Person instance. This runs a DELETE statement.
   */
  public Person delete(Person person) throws SQLException {
    String deletePerson = "DELETE FROM Person WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deletePerson);
      deleteStmt.setString(1, person.getUserName());
      deleteStmt.executeUpdate();
      // Return null so the caller can no longer operate on the Person instance.
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

  /**
   * Get the Person record by fetching it from your MySQL instance. This runs a SELECT statement and
   * returns a single Person instance.
   */
  public Person getPersonFromUserName(String userName) throws SQLException {
    String selectPerson = "SELECT UserName,FirstName,LastName,PassWord,Email,Phone FROM Person WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPerson);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      if (results.next()) {
        String resultUserName = results.getString("UserName");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        Person person = new Person(resultUserName, firstName, lastName, password, email, phone);
        return person;
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

  /**
   * Get the matching Person records by fetching from your MySQL instance. This runs a SELECT
   * statement and returns a list of matching Persons.
   */
  public List<Person> getPersonsFromFirstName(String firstName) throws SQLException {
    List<Person> persons = new ArrayList<>();
    String selectPersons = "SELECT UserName,FirstName,LastName,PassWord,Email,Phone FROM Person WHERE FirstName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPersons);
      selectStmt.setString(1, firstName);
      results = selectStmt.executeQuery();
      while (results.next()) {
        String userName = results.getString("UserName");
        String resultFirstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String password = results.getString("PassWord");
        String email = results.getString("Email");
        String phone = results.getString("Phone");
        Person person = new Person(userName, resultFirstName, lastName, password, email, phone);
        persons.add(person);
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
    return persons;
  }
}