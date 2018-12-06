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

public class CommentsDao {

  protected ConnectionManager connectionManager;

  private static CommentsDao instance = null;

  protected CommentsDao() {
    connectionManager = new ConnectionManager();
  }

  public static CommentsDao getInstance() {
    if (instance == null) {
      instance = new CommentsDao();
    }
    return instance;
  }

  public Comments create(Comments comment) throws SQLException {
    String insertComment = "INSERT INTO Comments(CommentId,UserName,ReportId,CommentContent,CommentTimeStamp) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertComment, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setLong(1, comment.getCommentId());
      insertStmt.setString(2, comment.getUser().getUserName());
      insertStmt.setLong(3, comment.getReport().getReportId());
      insertStmt.setString(4, comment.getCommentContent());
      insertStmt.setTimestamp(5, new Timestamp(comment.getCommentTimestamp().getTime()));
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      long commentId = -1;
      if (resultKey.next()) {
        commentId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      comment.setCommentId(commentId);
      return comment;
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
   * Update the content of the Comments instance. This runs a UPDATE statement.
   */
  public Comments updateContent(Comments comment, String commentContent) throws SQLException {
    String updateComment = "UPDATE Comments SET CommentContent=?,CommentTimeStamp=? WHERE CommentId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateComment);
      updateStmt.setString(1, commentContent);
      Date newCommentTimestamp = new Date();
      updateStmt.setTimestamp(2, new Timestamp(newCommentTimestamp.getTime()));
      updateStmt.setLong(3, comment.getCommentId());
      updateStmt.executeUpdate();
      // Update the blogComment param before returning to the caller.
      comment.setCommentContent(commentContent);
      comment.setCommentTimestamp(newCommentTimestamp);
      return comment;
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
   * Delete the Comments instance. This runs a DELETE statement.
   */
  public Comments delete(Comments comment) throws SQLException {
    String deleteComment = "DELETE FROM Comments WHERE CommentId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteComment);
      deleteStmt.setLong(1, comment.getCommentId());
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
   * Get the Comments record by fetching it from your MySQL instance. This runs a SELECT statement
   * and returns a single Person instance.
   */
  public Comments getCommentsFromCommentId(long commentId) throws SQLException {
    String selectComment = "SELECT CommentId,UserName,ReportId,CommentContent,CommentTimeStamp FROM Comments WHERE CommentId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectComment);
      selectStmt.setLong(1, commentId);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      CrimeReportsDao crimeReportsDao = CrimeReportsDao.getInstance();
      if (results.next()) {
        Long resultCommentId = results.getLong("CommentId");
        Users user = usersDao.getUserFromUserName(results.getString("UserName"));
        CrimeReports report = crimeReportsDao.getReportById(results.getLong("ReportId"));
        String commentContent = results.getString("CommentContent");
        Date commentTimestamp = new Date(results.getTimestamp("CommentTimeStamp").getTime());
        Comments comment = new Comments(resultCommentId, user, report, commentContent,
            commentTimestamp);
        return comment;
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
   * Get the all the Comments for a crime report.
   */
  public List<Comments> getCommentsForCrimeReport(CrimeReports report) throws SQLException {
    List<Comments> comments = new ArrayList<>();
    String selectComments = "SELECT CommentId,UserName,CommentContent,CommentTimeStamp FROM BlogComments WHERE ReportId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectComments);
      selectStmt.setLong(1, report.getReportId());
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      while (results.next()) {
        long commentId = results.getInt("CommentId");
        String commentContent = results.getString("CommentContent");
        Date commentTimeStamp = new Date(results.getTimestamp("CommentTimeStamp").getTime());
        Users user = usersDao.getUserFromUserName("UserName");
        Comments comment = new Comments(commentId, user, report, commentContent, commentTimeStamp);
        comments.add(comment);
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