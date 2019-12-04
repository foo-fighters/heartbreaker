package brickingbad.services.persistence;

import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.authentication.User;
import brickingbad.services.MongoDBService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBAuthenticationAdapter implements IAuthenticationAdapter {

  private static MongoCollection<User> usersCollection = MongoDBService.getDatabase().getCollection("users", User.class);

  @Override
  public void addUser(User user) throws IllegalArgumentException {
    ArrayList<String> names = getNames();
    if (names.contains(user.name)) {
      throw new IllegalArgumentException("A user with that name already exists.");
    } else {
      usersCollection.insertOne(user);
    }
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
    MongoCursor<User> cursor = usersCollection.find().iterator();
    try {
      while (cursor.hasNext()) {
        names.add(cursor.next().name);
      }
    } finally {
      cursor.close();
    }
    return names;
  }

}
