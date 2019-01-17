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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Map<String, String> messages = new HashMap<String, String>();
    request.setAttribute("messages", messages);
    //Just render the JSP.
    request.getRequestDispatcher("/ReportAdd.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Map<String, String> messages = new HashMap<String, String>();
    request.setAttribute("messages", messages);

    String userName = request.getParameter("username");
    if (userName == null || userName.trim().isEmpty()) {
      messages.put("success", "Invalid UserName");
    } else {
      Users user = new Users(userName);
      String occurredTimeStamp = request.getParameter("occurredTimeStamp");
      //String reportedTimeStamp = request.getParameter("occurredTimeStamp");
      String initialCallType = request.getParameter("initialCallTypeId");
//      System.out.println(initialCallType);
      int initialCallTypeId = getCrimeCategoryId(initialCallType);
      int finalCallTypeId = initialCallTypeId;
      String beat = request.getParameter("beatsectorId");
      int beatId = getBeatId(beat);
      String neighborhood = request.getParameter("neighborhoodId");
      int neighborhoodId = getNeighborhoodId(neighborhood);
      String zipcode = request.getParameter("zipcodeId");
      int zipcodeId = getZipcodeId(zipcode);
      Date occurred = new Date();
      Date reported = new Date();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try {
        occurred = dateFormat.parse(occurredTimeStamp);
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
        CrimeReports crimeReport = new CrimeReports(user, occurred, reported, initial, finalCall,
            beatSector, neighbor, zip);
        crimeReport = crimeReportsDao.create(crimeReport);
        messages.put("success", "Successfully created " + userName);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    request.getRequestDispatcher("/ReportAdd.jsp").forward(request, response);
  }


  public int getCrimeCategoryId(String category) {
    switch (category) {
      case "LOITERING":
        return 3;
      case "RAPE":
        return 4;
      case "NARCOTIC":
        return 5;
      case "WEAPON":
        return 15;
      case "DUI":
        return 23;
      case "GAMBLE":
        return 24;
      case "ARSON":
        return 29;
      case "HOMICIDE":
        return 57;
      case "TRESPASS":
        return 86;
      default:
        return 145;
    }
  }

  public int getBeatId(String beat) {
    switch (beat) {
      case "W":
        return 1;
      case "CS":
        return 2;
      case "W2":
        return 3;
      case "L1":
        return 4;
      case "C1":
        return 5;
      case "K3":
        return 6;
      case "R2":
        return 7;
      case "J3":
        return 9;
      case "J1":
        return 10;
      case "F1":
        return 11;
      case "G1":
        return 12;
      case "INV":
        return 13;
      case "H1":
        return 14;
      case "N3":
        return 15;
      default:
        return 8;
    }
  }

  public int getNeighborhoodId(String neighbor) {
    switch (neighbor) {
      case "BELLTOWN":
        return 19;
      case "GREENWOOD":
        return 26;
      case "LAKECITY":
        return 34;
      case "CAPITOLHILL":
        return 42;
      case "UNIVERSITY":
        return 48;
      case "QUEENANNE":
        return 55;
      case "FREMONT":
        return 57;
      case "SEATTLE":
        return 60;
      default:
        return 52;
    }
  }

  public int getZipcodeId(String zipcode) {
    switch (zipcode) {
      case "98103":
        return 1;
      case "98115":
        return 3;
      case "98104":
        return 5;
      case "98109":
        return 6;
      case "98116":
        return 7;
      case "98121":
        return 8;
      case "98102":
        return 14;
      case "98122":
        return 18;
      case "98107":
        return 27;
      default:
        return 42;
    }
  }
}
