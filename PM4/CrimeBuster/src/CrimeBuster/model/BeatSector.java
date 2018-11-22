package crimebuster.model;

public class BeatSector {

  protected int beatId;
  protected String beat;
  protected String sector;

  public BeatSector(int beatId, String beat, String sector) {
    this.beatId = beatId;
    this.beat = beat;
    this.sector = sector;
  }

  public int getBeatId() {
    return beatId;
  }

  public void setBeatId(int beatId) {
    this.beatId = beatId;
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
