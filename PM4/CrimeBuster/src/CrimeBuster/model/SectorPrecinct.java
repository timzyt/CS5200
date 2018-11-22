package crimebuster.model;

public class SectorPrecinct {

  protected String sector;
  protected Precinct precinct;

  public enum Precinct {
    NORTH, WEST, EAST, SOUTH, SOUTHEAST, SOUTHWEST, UNKNOWN
  }

  public SectorPrecinct(String sector, Precinct precinct) {
    this.sector = sector;
    this.precinct = precinct;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public Precinct getPrecinct() {
    return precinct;
  }

  public void setPrecinct(Precinct precinct) {
    this.precinct = precinct;
  }
}
