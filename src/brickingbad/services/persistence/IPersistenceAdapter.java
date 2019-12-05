package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;

import java.util.ArrayList;
import java.util.List;

public interface IPersistenceAdapter {

  Save getSaveByName(String name);
  void save(Save save);
  List<String> getSaveNames();

}
