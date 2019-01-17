package crimebuster.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import crimebuster.model.CrimeCategory;

/**
 * Data access object (DAO) class to interact with the underlying Person table in your MySQL
 * instance. This is used to store {@link Person} into your MySQL instance and retrieve {@link
 * Person} from MySQL instance.
 */
public class CrimeCategoryDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CrimeCategoryDao instance = null;

  protected CrimeCategoryDao() {
    connectionManager = new ConnectionManager();
  }

  public static CrimeCategoryDao getInstance() {
    if (instance == null) {
      instance = new CrimeCategoryDao();
    }
    return instance;
  }

  /**
   * Get the CrimeCategoryId by PrimaryOffenseDescription
   */
  public CrimeCategory getCrimeCategoryByPrimaryOffenseDescription(String primaryOffenseDescription) throws SQLException {
    String selectCrimeCategoryId = "SELECT CrimeCategoryId FROM CrimeCategory WHERE PrimaryOffenseDescription = ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCrimeCategoryId);
      selectStmt.setString(1, primaryOffenseDescription);
      results = selectStmt.executeQuery();
      if (results.next()) {
    	  int resultCrimeCategoryId = results.getInt("CrimeCategoryId");
    	  CrimeCategory crimeCategory = new CrimeCategory(resultCrimeCategoryId);
    	  return crimeCategory;
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
    }
    return null;
  }
}