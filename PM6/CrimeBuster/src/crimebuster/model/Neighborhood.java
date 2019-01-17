package crimebuster.model;

public class Neighborhood {

  protected int neighborhoodId;
  protected String neighborhoodName;

  public Neighborhood(int neighborhoodId, String neighborhoodName) {
    this.neighborhoodId = neighborhoodId;
    this.neighborhoodName = neighborhoodName;
  }
  
  public Neighborhood(int neighborhoodId) {
	  this.neighborhoodId = neighborhoodId;
  }

  public int getNeighborhoodId() {
    return neighborhoodId;
  }

  public void setNeighborhoodId(int neighborhoodId) {
    this.neighborhoodId = neighborhoodId;
  }

  public String getNeighborhoodName() {
    return neighborhoodName;
  }

  public void setNeighborhoodName(String neighborhoodName) {
    this.neighborhoodName = neighborhoodName;
  }
}
