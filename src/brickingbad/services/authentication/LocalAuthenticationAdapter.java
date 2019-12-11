package brickingbad.services.authentication;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.encryption.Decoder;
import brickingbad.services.encryption.Encoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocalAuthenticationAdapter implements IAuthenticationAdapter {

  private final String usersPath = "storage/users/";

  @Override
  public void addUser(User user) throws IllegalArgumentException {
    createUsersDirectoryIfDoesntExist();
    ArrayList<String> names = getNames();
    if (names.contains(user.name)) {
      throw new IllegalArgumentException("A user with that username already exists.");
    } else {
      String encodedPassword = Encoder.encodeString(user.password);
      String encodedName = Encoder.encodeString(user.name);
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(usersPath + encodedName + ".txt"));
        writer.write(encodedPassword);
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public User findUserByName(String name) throws IllegalArgumentException {
    List<String> names = getNames()
                              .stream().map(Decoder::decodeString)
                              .collect(Collectors.toList());
    if (!names.contains(name)) {
      throw new IllegalArgumentException("A user with that name doesn't exist.");
    } else {
      try {
        String encodedPassword = Files.readString(Paths.get(usersPath + Encoder.encodeString(name) + ".txt"), StandardCharsets.US_ASCII);
        String decodedPassword = Decoder.decodeString(encodedPassword);
        return new User(name, decodedPassword);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public ArrayList<String> getNames() {
    ArrayList<String> names = new ArrayList<>();
    File folder = new File(usersPath);
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

  private void createUsersDirectoryIfDoesntExist() {
    Path path = Paths.get(usersPath);
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
