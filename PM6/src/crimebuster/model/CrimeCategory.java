package crimebuster.model;

public class CrimeCategory {

  protected int crimeCategoryId;
  protected String primaryOffenseDescription;
  protected String crimeSubCategoryName;

  public CrimeCategory(int crimeCategoryId, String primaryOffenseDescription,
      String crimeSubCategoryName) {
    this.crimeCategoryId = crimeCategoryId;
    this.primaryOffenseDescription = primaryOffenseDescription;
    this.crimeSubCategoryName = crimeSubCategoryName;
  }
  
  public CrimeCategory(int crimeCategoryId) {
	    this.crimeCategoryId = crimeCategoryId;
	  }

  public int getCrimeCategoryId() {
    return crimeCategoryId;
  }

  public void setCrimeCategoryId(int crimeCategoryId) {
    this.crimeCategoryId = crimeCategoryId;
  }

  public String getPrimaryOffenseDescription() {
    return primaryOffenseDescription;
  }

  public void setPrimaryOffenseDescription(String primaryOffenseDescription) {
    this.primaryOffenseDescription = primaryOffenseDescription;
  }

  public String getCrimeSubCategoryName() {
    return crimeSubCategoryName;
  }

  public void setCrimeSubCategoryName(String crimeSubCategoryName) {
    this.crimeSubCategoryName = crimeSubCategoryName;
  }
}
