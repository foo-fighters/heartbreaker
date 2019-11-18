package brickingbad.services.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseService {

  private static MongoClientURI uri = new MongoClientURI(
          "mongodb+srv://admin:comp302@bricking-bad-lw1qn.mongodb.net/test?retryWrites=true&w=majority");

  private static MongoClient mongoClient = new MongoClient(uri);
  private static MongoDatabase database = mongoClient.getDatabase("brickingBad");

  private static MongoCollection<Document> gamesCollection = database.getCollection("games");
  private static MongoCollection<Document> usersCollection = database.getCollection("users");



}
