package crimebuster.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crimebuster.dal.CrimeReportsDao;
import crimebuster.dal.UsersDao;
import crimebuster.model.BeatSector;
import crimebuster.model.CrimeCategory;
import crimebuster.model.CrimeReports;
import crimebuster.model.Neighborhood;
import crimebuster.model.Users;
import crimebuster.model.Zipcode;

/**
 * Servlet implementation class ReportAdd
 */
@WebServlet("/reportadd")
public class ReportAdd extends HttpServlet {
	public CrimeReportsDao crimeReportsDao;
       
    public void init() throws ServletException {
    		crimeReportsDao = CrimeReportsDao.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, String> messages = new HashMap<String, String>();
	     request.setAttribute("messages", messages);
	        //Just render the JSP.   
	     request.getRequestDispatcher("/ReportAdd.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		request.setAttribute("messages", messages);
		
		String reportId = request.getParameter("reportId");
		if(reportId == null || reportId.trim().isEmpty()) {
			messages.put("success", "Invalid ReportId");
		} else {
			String userName = request.getParameter("username");
			Users user = new Users(userName);
			String occurredTimeStamp = request.getParameter("occurredTimeStamp");
			String reportedTimeStamp = request.getParameter("reportedTimeStamp");
			String initialCallType = request.getParameter("initialCallType");
			int initialCallTypeId = Integer.parseInt(initialCallType);
			String finalCallType = request.getParameter("finalCallType");
			int finalCallTypeId = Integer.parseInt(finalCallType);
			String beat = request.getParameter("beat");
			int beatId = Integer.parseInt(beat);
			String neighborhood = request.getParameter("neighborhood");
			int neighborhoodId = Integer.parseInt(neighborhood);
			String zipcode = request.getParameter("zipcode");
			int zipcodeId = Integer.parseInt(zipcode);
			Date occurred = new Date();
			Date reported = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				occurred = dateFormat.parse(occurredTimeStamp);
				reported = dateFormat.parse(reportedTimeStamp);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			CrimeCategory initial = new CrimeCategory(initialCallTypeId);
			CrimeCategory finalCall = new CrimeCategory(finalCallTypeId);
			BeatSector beatSector = new BeatSector(beatId);
			Neighborhood neighbor = new Neighborhood(neighborhoodId);
			Zipcode zip = new Zipcode(zipcodeId);
			try {
				CrimeReports crimeReport = new CrimeReports(user, occurred, reported, initial, finalCall, beatSector, neighbor, zip);
				crimeReport = crimeReportsDao.create(crimeReport);
				messages.put("success", "Successfully created " + userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			
		}
		request.getRequestDispatcher("/ReportAdd.jsp").forward(request, response);
	}

}
