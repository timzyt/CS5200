package crimebuster.model;

import java.util.Date;

public class Comments {

  protected long commentId;
  protected Users user;
  protected CrimeReports report;
  protected String commentContent;
  protected Date commentTimestamp;

  public Comments(long commentId, Users user, CrimeReports report, String commentContent,
      Date commentTimestamp) {
    this.commentId = commentId;
    this.user = user;
    this.report = report;
    this.commentContent = commentContent;
    this.commentTimestamp = commentTimestamp;
  }

  public Comments(long commentId) {
    this.commentId = commentId;
  }

  public Comments(Users user, CrimeReports report, String commentContent,
      Date commentTimestamp) {
    this.user = user;
    this.report = report;
    this.commentContent = commentContent;
    this.commentTimestamp = commentTimestamp;
  }

  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  public CrimeReports getReport() {
    return report;
  }

  public void setReport(CrimeReports report) {
    this.report = report;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public Date getCommentTimestamp() {
    return commentTimestamp;
  }

  public void setCommentTimestamp(Date commentTimestamp) {
    this.commentTimestamp = commentTimestamp;
  }
}
