package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LocalPersistenceAdapter implements IPersistenceAdapter {

  private final String savePath = "storage/saves/";

  @Override
  public Save getSaveByName(String name) {
    Save save = null;
    try {
      String json = Files.readString(Paths.get(savePath + name + ".json"), StandardCharsets.US_ASCII);
      Gson gson = new Gson();
      save = gson.fromJson(json, Save.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return save;
  }

  @Override
  public void save(Save save) {
    Gson gson = new Gson();
    String json = gson.toJson(save);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(savePath + save.name + ".json"));
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> getSaveNames() {
    ArrayList<String> names = new ArrayList<>();
    File folder = new File(savePath);
    File[] listOfFiles = folder.listFiles();
    for (File file : listOfFiles) {
      String fullName = file.getName();
      String[] tokens = fullName.split("[.]");
      names.add(tokens[0]);
    }
    return names;
  }

}
