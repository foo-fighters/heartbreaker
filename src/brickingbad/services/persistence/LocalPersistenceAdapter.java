package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LocalPersistenceAdapter implements IPersistenceAdapter {

  @Override
  public Save getSaveByName(String name) {

  }

  @Override
  public void save(Save save) {
    Gson gson = new Gson();
    String json = gson.toJson(save);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("storage/saves/" + save.name + ".json"));
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> getSaveNames() {

  }

}
