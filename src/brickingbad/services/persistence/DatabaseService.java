package brickingbad.services.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DatabaseService {

  MongoClientURI uri = new MongoClientURI(
          "mongodb+srv://admin:comp302@bricking-bad-lw1qn.mongodb.net/test?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase database = mongoClient.getDatabase("test");


}
