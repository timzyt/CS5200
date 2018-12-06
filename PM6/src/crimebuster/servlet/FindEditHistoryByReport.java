package crimebuster.servlet;

import crimebuster.dal.CrimeReportsDao;
import crimebuster.dal.EditHistoryDao;
import crimebuster.model.CrimeReports;
import crimebuster.model.EditHistory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FindEditHistoryByUser
 */
@WebServlet("/findedithistorybyreport")
public class FindEditHistoryByReport extends HttpServlet {

  private static final long serialVersionUID = 1L;
  protected EditHistoryDao editHistoryDao;

  @Override
  public void init() throws ServletException {
    editHistoryDao = EditHistoryDao.getInstance();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Map<String, String> messages = new HashMap<>();
    request.setAttribute("messages", messages);
    List<EditHistory> editHistories = new ArrayList<>();
    String reportId = request.getParameter("ReportId");
    if (reportId == null || reportId.trim().isEmpty()) {
      messages.put("success", "Please enter a valid report id.");
    } else {
      try {
        CrimeReports report = CrimeReportsDao.getInstance().getReportById(Long.parseLong(reportId));
        editHistories = editHistoryDao.getEditHistoryForCrimeReport(report);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for report id " + reportId);
      messages.put("previousReportId", reportId);
    }
    request.setAttribute("editHistories", editHistories);
    request.getRequestDispatcher("/FindEditHistoryByReport.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Map<String, String> messages = new HashMap<>();
    request.setAttribute("messages", messages);
    List<EditHistory> editHistories = new ArrayList<>();
    String reportId = request.getParameter("ReportId");
    if (reportId == null || reportId.trim().isEmpty()) {
      messages.put("success", "Please enter a valid report id.");
    } else {
      try {
        CrimeReports report = CrimeReportsDao.getInstance().getReportById(Long.parseLong(reportId));
        editHistories = editHistoryDao.getEditHistoryForCrimeReport(report);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for report id " + reportId);
    }
    request.setAttribute("editHistories", editHistories);
    request.getRequestDispatcher("/FindEditHistoryByReport.jsp").forward(request, response);
  }
}