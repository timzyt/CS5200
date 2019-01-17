package crimebuster.model;

import java.util.Date;

public class Users extends Person {

  protected Date registeredAt;

  public Users(String userName, String firstName, String lastName, String password, String email,
      String phone, Date registeredAt) {
    super(userName, firstName, lastName, password, email, phone);
    this.registeredAt = registeredAt;
  }

  public Users(String userName, String password, Date registeredAt) {
    super(userName, password);
    this.registeredAt = registeredAt;
  }

  public Users(String firstName, String lastName, String password, String email, String phone,
      Date registeredAt) {
    super(firstName, lastName, password, email, phone);
    this.registeredAt = registeredAt;
  }
  
  public Users(String userName) {
	  super(userName);
  }

  public Date getRegisteredAt() {
    return registeredAt;
  }

  public void setRegisteredAt(Date registeredAt) {
    this.registeredAt = registeredAt;
  }
}