package brickingbad.services.persistence;

import brickingbad.domain.game.authentication.User;
import brickingbad.domain.game.persistence.Save;
import brickingbad.services.encryption.Decoder;
import brickingbad.services.encryption.Encoder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LocalPersistenceAdapter implements IPersistenceAdapter {

  private final String savePath = "storage/saves/";

  @Override
  public Save getSaveByName(String name, User user) {
    Save save = null;
    try {
      String json = Files.readString(Paths.get(getUserSavePath(user) + name + ".txt"), StandardCharsets.US_ASCII);
      json = Decoder.decodeString(json);
      Gson gson = new Gson();
      save = gson.fromJson(json, Save.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return save;
  }

  @Override
  public void save(Save save, User user) {
    createUserSaveDirectoryIfDoesntExist(user);
    Gson gson = new Gson();
    String json = gson.toJson(save);
    json = Encoder.encodeString(json);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(getUserSavePath(user) + save.name + ".txt"));
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> getSaveNames(User user) {
    ArrayList<String> names = new ArrayList<>();
    File folder = new File(getUserSavePath(user));
    File[] listOfFiles = folder.listFiles();
    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        String fullName = file.getName();
        String[] tokens = fullName.split("[.]");
        names.add(tokens[0]);
      }
    }
    return names;
  }

  private void createUserSaveDirectoryIfDoesntExist(User user) {
    Path path = Paths.get(getUserSavePath(user));
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String getUserSavePath(User user) {
    return savePath + Encoder.encodeString(user.name) + "/";
  }

}
