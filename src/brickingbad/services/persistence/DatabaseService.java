package brickingbad.services.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseService {

  private static MongoClientURI uri = new MongoClientURI(
          "mongodb+srv://admin:comp302@bricking-bad-lw1qn.mongodb.net/test?retryWrites=true&w=majority");

  private static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                                                   fromProviders(PojoCodecProvider.builder().automatic(true).build()));

  private static MongoClient mongoClient = new MongoClient(uri);
  private static MongoDatabase database = mongoClient.getDatabase("brickingBad")
                                                     .withCodecRegistry(pojoCodecRegistry);

}
