package crimebuster.servlet;
import crimebuster.dal.*;

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

import crimebuster.dal.UsersDao;
import crimebuster.model.Users;
import crimebuster.dal.CrimeReportsDao;
import crimebuster.model.CrimeReports;
import crimebuster.dal.CommentsDao;
import crimebuster.model.Comments;


@WebServlet("/reportcomments")
public class ReportComments extends HttpServlet {
	protected CommentsDao commentsDao;  
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
    
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        List<Comments> comments = new ArrayList<Comments>();
        
		// Retrieve Comments depending on valid PostId or UserName.
        String reportId = req.getParameter("reportId");
        //String userName = req.getParameter("username");
        
        try {
	        if (reportId != null && !reportId.trim().isEmpty()) {
	        	// If the reportid param is provided then ignore the username param.
	        	CrimeReports crimeReport = new CrimeReports(Long.parseLong(reportId));
	        	comments = commentsDao.getCommentsForCrimeReport(crimeReport);
	        	messages.put("reportId", "Comments for ReportId " + reportId);
	        }  else {
	        	messages.put("reportId", "Invalid ReportId.");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.setAttribute("comments", comments);
        req.getRequestDispatcher("/ReportComments.jsp").forward(req, resp);
	}
}