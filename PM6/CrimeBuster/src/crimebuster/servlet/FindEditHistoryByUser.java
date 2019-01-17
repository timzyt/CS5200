package crimebuster.servlet;

import crimebuster.dal.*;
import crimebuster.model.EditHistory;
import crimebuster.model.Users;

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
@WebServlet("/findedithistorybyuser")
public class FindEditHistoryByUser extends HttpServlet {
	
	  protected EditHistoryDao editHistoryDao;

	  @Override
	  public void init() throws ServletException {
	    editHistoryDao = EditHistoryDao.getInstance();
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);
	    List<EditHistory> editHistories = new ArrayList<>();
	    String userName = request.getParameter("UserName");
	    if (userName == null || userName.trim().isEmpty()) {
	      messages.put("success", "Please enter a valid username.");
	    } else {
	      try {
	        Users user = UsersDao.getInstance().getUserFromUserName(userName);
	        editHistories = editHistoryDao.getEditHistoryForUser(user);
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	      messages.put("success", "Displaying results for " + userName);
	      messages.put("previousUserName", userName);
	    }
	    request.setAttribute("editHistories", editHistories);
	    request.getRequestDispatcher("/FindEditHistoryByUser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);
	    List<EditHistory> editHistories = new ArrayList<>();
	    String userName = request.getParameter("UserName");
	    if (userName == null || userName.trim().isEmpty()) {
	      messages.put("success", "Please enter a valid username.");
	    } else {
	      try {
	        Users user = UsersDao.getInstance().getUserFromUserName(userName);
	        editHistories = editHistoryDao.getEditHistoryForUser(user);
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	      messages.put("success", "Displaying results for " + userName);
	    }
	    request.setAttribute("editHistories", editHistories);
	    request.getRequestDispatcher("/FindEditHistoryByUser.jsp").forward(request, response);
	  }
}
