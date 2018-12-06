package crimebuster.model;

public class Admin extends Person {

  protected int level;

  public Admin(String userName, String firstName, String lastName, String password, String email,
      String phone, int level) {
    super(userName, firstName, lastName, password, email, phone);
    this.level = level;
  }

  public Admin(String userName, String password, int level) {
    super(userName, password);
    this.level = level;
  }

  public Admin(String firstName, String lastName, String password, String email, String phone,
      int level) {
    super(firstName, lastName, password, email, phone);
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    level = level;
  }
}
