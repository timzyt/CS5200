package crimebuster.model;

public class Zipcode {

  protected int zipcodeId;
  protected String zipcode;

  public Zipcode(int zipcodeId, String zipcode) {
    this.zipcodeId = zipcodeId;
    this.zipcode = zipcode;
  }
  
  public Zipcode(int zipcodeId) {
	  this.zipcodeId = zipcodeId;
  }

  public int getZipcodeId() {
    return zipcodeId;
  }

  public void setZipcodeId(int zipcodeId) {
    this.zipcodeId = zipcodeId;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }
}
