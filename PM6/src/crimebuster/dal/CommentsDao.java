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
    String insertComment = "INSERT INTO Comments(UserName,ReportId,CommentContent,CommentTimeStamp) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertComment, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, comment.getUser().getUserName());
      insertStmt.setLong(2, comment.getReport().getReportId());
      insertStmt.setString(3, comment.getCommentContent());
      insertStmt.setTimestamp(4, new Timestamp(comment.getCommentTimestamp().getTime()));
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
   * Get all the Comments by commentId.
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
   * Get all the Comments by reportId.
   */
  public List<Comments> getCommentsForCrimeReport(long reportId) throws SQLException {
    List<Comments> comments = new ArrayList<>();
    String selectComments = "SELECT CommentId,UserName,ReportId,CommentContent,CommentTimeStamp FROM Comments WHERE ReportId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectComments);
      selectStmt.setLong(1, reportId);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      while (results.next()) {
        long commentId = results.getInt("CommentId");
        CrimeReports crimeReport = new CrimeReports(reportId);
        String commentContent = results.getString("CommentContent");
        Date commentTimeStamp = new Date(results.getTimestamp("CommentTimeStamp").getTime());
        Users user = usersDao.getUserFromUserName("UserName");
        Comments comment = new Comments(commentId, user, crimeReport, commentContent, commentTimeStamp);
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
  
  /**
   * Get all the Comments by reportId.
   */
  public List<Comments> getBlogCommentsForUser(Users user) throws SQLException {
    List<Comments> comments = new ArrayList<>();
    String selectComments = "SELECT CommentId,userName,report,CommentContent,CommentTimeStamp FROM Comments WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectComments);
      selectStmt.setString(1, user.getUserName());
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      while (results.next()) {
        long commentId = results.getInt("CommentId");
        long reportId = results.getLong("ReportId"); 
        String commentContent = results.getString("CommentContent");
        Date commentTimeStamp = new Date(results.getTimestamp("CommentTimeStamp").getTime());
        CrimeReports report = new CrimeReports(reportId);
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