package crimebuster.model;

import java.util.Date;

public class EditHistory {

  protected long editHistoryId;
  protected Users user;
  protected CrimeReports report;
  protected Date editTime;
  protected String editComment;

  public EditHistory(long editHistoryId, Users user, CrimeReports report, Date editTime,
      String editComment) {
    this.editHistoryId = editHistoryId;
    this.user = user;
    this.report = report;
    this.editTime = editTime;
    this.editComment = editComment;
  }

  public EditHistory(int editHistoryId) {
    this.editHistoryId = editHistoryId;
  }

  public EditHistory(Users user, CrimeReports report, Date editTime,
      String editComment) {
    this.user = user;
    this.report = report;
    this.editTime = editTime;
    this.editComment = editComment;
  }

  public long getEditHistoryId() {
    return editHistoryId;
  }

  public void setEditHistoryId(int editHistoryId) {
    this.editHistoryId = editHistoryId;
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

  public Date getEditTime() {
    return editTime;
  }

  public void setEditTime(Date editTime) {
    this.editTime = editTime;
  }

  public String getEditComment() {
    return editComment;
  }

  public void setEditComment(String editComment) {
    this.editComment = editComment;
  }
}
