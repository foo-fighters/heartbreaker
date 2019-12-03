package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;

import java.util.List;

public class LocalAdapter implements IPersistenceAdapter {

  @Override
  public Save getSaveByName(String name) {
    return null;
  }

  @Override
  public void save(Save save) {

  }

  @Override
  public List<String> getSaveNames() {
    return null;
  }

}
