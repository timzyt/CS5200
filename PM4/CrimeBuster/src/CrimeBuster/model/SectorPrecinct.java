package crimebuster.model;

public class SectorPrecinct {

  protected int sectorId;
  protected String sector;
  protected Precinct precinct;

  public enum Precinct {
    NORTH, WEST, EAST, SOUTH, SOUTHEAST, SOUTHWEST, UNKNOWN
  }

  public SectorPrecinct(int sectorId, String sector, Precinct precinct) {
    this.sectorId = sectorId;
    this.sector = sector;
    this.precinct = precinct;
  }

  public int getSectorId() {
    return sectorId;
  }

  public void setSectorId(int sectorId) {
    this.sectorId = sectorId;
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
