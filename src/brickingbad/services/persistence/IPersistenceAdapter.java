package brickingbad.services.persistence;

import brickingbad.domain.game.authentication.User;
import brickingbad.domain.game.persistence.Save;

import java.util.ArrayList;
import java.util.List;

public interface IPersistenceAdapter {

  Save getSaveByName(String name, User user);
  void save(Save save, User user);
  List<String> getSaveNames(User user);

}
