package brickingbad.services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBService {

  private static final String clientURI = "mongodb+srv://admin:comp302@bricking-bad-lw1qn.mongodb.net/test?retryWrites=true&w=majority";

  private static MongoClientURI uri = new MongoClientURI(clientURI);

  private static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
          fromProviders(PojoCodecProvider.builder().automatic(true).build()));

  private static MongoClient mongoClient = new MongoClient(uri);
  private static MongoDatabase database = mongoClient.getDatabase("brickingBad")
                                                     .withCodecRegistry(pojoCodecRegistry);

  public static MongoDatabase getDatabase() {
    return database;
  }

}
