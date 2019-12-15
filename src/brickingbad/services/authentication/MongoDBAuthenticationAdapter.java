package brickingbad.services.authentication;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.MongoDBService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBAuthenticationAdapter implements IAuthenticationAdapter {

  private static final MongoCollection<User> usersCollection = MongoDBService.getDatabase().getCollection("users", User.class);

  @Override
  public boolean addUser(User user) throws IllegalArgumentException {
    ArrayList<String> names = getNames();
    if (names.contains(user.name)) {
      throw new IllegalArgumentException("A user with that name already exists.");
    } else {
      usersCollection.insertOne(user);
      return true;
    }
  }

  @Override
  public void deleteUser(User user) {
    usersCollection.deleteOne(eq("name", user.name));
  }

  @Override
  public User findUserByName(String name) throws IllegalArgumentException {
    User user = usersCollection.find(eq("name", name)).first();
    if (user == null) {
      throw new IllegalArgumentException("A user with that name doesn't exist.");
    } else {
      return user;
    }
  }

  @Override
  public ArrayList<String> getNames() {
    ArrayList<String> names = new ArrayList<>();
    try (MongoCursor<User> cursor = usersCollection.find().iterator()) {
      while (cursor.hasNext()) {
        names.add(cursor.next().name);
      }
    }
    return names;
  }

}
