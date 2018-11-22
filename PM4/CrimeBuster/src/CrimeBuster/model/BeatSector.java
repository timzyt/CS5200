package crimebuster.model;

public class BeatSector {

  protected String beat;
  protected String sector;

  public BeatSector(String beat, String sector) {
    this.beat = beat;
    this.sector = sector;
  }

  public String getBeat() {
    return beat;
  }

  public void setBeat(String beat) {
    this.beat = beat;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }
}
