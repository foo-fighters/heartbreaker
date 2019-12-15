package brickingbad.domain.game.authentication;

public class User {

  public String name;
  public String password;

  public User() {

  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  @Override
  public boolean equals(Object object) {
    User user = (User) object;
    return user.name.equals(this.name) &&
            user.password.equals(this.password);
  }

}
