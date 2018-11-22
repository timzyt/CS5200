package crimebuster.model;

public class CrimeCategory {

  protected String primaryOffenseDescription;
  protected String crimeSubCategoryName;

  public CrimeCategory(String primaryOffenseDescription, String crimeSubCategoryName) {
    this.primaryOffenseDescription = primaryOffenseDescription;
    this.crimeSubCategoryName = crimeSubCategoryName;
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
