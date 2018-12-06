package crimebuster.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import crimebuster.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditHistoryDao {

  protected ConnectionManager connectionManager;

  private static EditHistoryDao instance = null;

  protected EditHistoryDao() {
    connectionManager = new ConnectionManager();
  }

  public static EditHistoryDao getInstance() {
    if (instance == null) {
      instance = new EditHistoryDao();
    }
    return instance;
  }

  public EditHistory create(EditHistory editHistory) throws SQLException {
    String insertEditHistory = "INSERT INTO EditHistory(EditHistoryId,UserName,EditTime,ReportId,EditComment) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertEditHistory, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setLong(1, editHistory.getEditHistoryId());
      insertStmt.setString(2, editHistory.getUser().getUserName());
      insertStmt.setTimestamp(3, new Timestamp(editHistory.getEditTime().getTime()));
      insertStmt.setLong(4, editHistory.getReport().getReportId());
      insertStmt.setString(5, editHistory.getEditComment());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int editHistoryId = -1;
      if (resultKey.next()) {
        editHistoryId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      editHistory.setEditHistoryId(editHistoryId);
      return editHistory;
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
      if (resultKey != null) {
        resultKey.close();
      }
    }
  }

  /**
   * Get the all the EditHistory for a crime report.
   */
  public List<EditHistory> getEditHistoryForCrimeReport(CrimeReports report) throws SQLException {
    List<EditHistory> comments = new ArrayList<>();
    String selectHistories = "SELECT EditHistoryId,UserName,EditTime,ReportId,EditComment FROM EditHistory WHERE ReportId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectHistories);
      selectStmt.setLong(1, report.getReportId());
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      while (results.next()) {
        long commentId = results.getLong("EditHistoryId");
        Users user = usersDao.getUserFromUserName(results.getString("UserName"));
        Date editTime = new Date(results.getTimestamp("EditTime").getTime());
        String editComment = results.getString("EditComment");
        EditHistory history = new EditHistory(commentId, user, report, editTime, editComment);
        comments.add(history);
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
    return comments;
  }

  /**
   * Get the all the EditHistory for a user.
   */
  public List<EditHistory> getEditHistoryForUser(Users user) throws SQLException {
    List<EditHistory> comments = new ArrayList<>();
    String selectHistories = "SELECT EditHistoryId,UserName,EditTime,ReportId,EditComment FROM EditHistory WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectHistories);
      selectStmt.setString(1, user.getUserName());
      results = selectStmt.executeQuery();
      CrimeReportsDao crimeReportsDao = CrimeReportsDao.getInstance();
      while (results.next()) {
        long commentId = results.getLong("EditHistoryId");
        CrimeReports report = crimeReportsDao.getReportById(results.getLong("ReportId"));
        Date editTime = new Date(results.getTimestamp("EditTime").getTime());
        String editComment = results.getString("EditComment");
        EditHistory history = new EditHistory(commentId, user, report, editTime, editComment);
        comments.add(history);
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
    return comments;
  }
}
