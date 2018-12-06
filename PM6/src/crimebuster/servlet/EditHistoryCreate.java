package crimebuster.servlet;

import crimebuster.dal.CrimeReportsDao;
import crimebuster.dal.EditHistoryDao;
import crimebuster.dal.UsersDao;
import crimebuster.model.CrimeReports;
import crimebuster.model.EditHistory;
import crimebuster.model.Users;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditHistory
 */
@WebServlet("/edithistorycreate")
public class EditHistoryCreate extends HttpServlet {

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
    // render the JSP
    request.getRequestDispatcher("/EditHistoryCreate.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Map for storing messages
    Map<String, String> messages = new HashMap<>();
    request.setAttribute("messages", messages);
    // Create an EditHistory
    UsersDao usersDao = UsersDao.getInstance();
    CrimeReportsDao crimeReportsDao = CrimeReportsDao.getInstance();
    try {
      Users user = usersDao.getUserFromUserName(request.getParameter("UserName"));
      Date editTime = new Timestamp(Long.parseLong(request.getParameter("EditTime")));
      CrimeReports report = crimeReportsDao
          .getReportById(Long.parseLong(request.getParameter("ReportId")));
      String editComment = request.getParameter("EditComment");
      EditHistory editHistory = new EditHistory(user, report, editTime, editComment);
      messages.put("success",
          "Successfully created the edit history of report id " + report.getReportId());
      EditHistoryDao.getInstance().create(editHistory);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    request.getRequestDispatcher("/EditHistoryCreate.jsp").forward(request, response);
  }

}
