package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import model.Accent;
import model.Author;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccentRepository{

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection mongoCollection;

    static {
        String hostName = "127.0.0.1";
        String authUser = "myUserAdmin";
        String authPass = "123456";

        String clientUrl = "mongodb://" + authUser + ":" + authPass + "@" + hostName + ":" + 27017 + "/?authSource=admin";
        System.out.println(clientUrl);
        MongoClientURI uri = new MongoClientURI(clientUrl);
        mongoClient = new com.mongodb.MongoClient(uri);
        mongoDatabase = mongoClient.getDatabase("audio_book");

        if (!mongoDatabase.listCollectionNames().into(new ArrayList<String>()).contains("accent")){
            mongoDatabase.createCollection("accent");
        }

        mongoCollection = mongoDatabase.getCollection("accent");
    }

    public static void createAccent(Accent accent){
        Document document = new Document();
        document.put("name", accent.getName());
        mongoCollection.insertOne(document);
    }

    public static List<Accent> getAccents(){
        Iterable<Document> documents = mongoCollection.find();
        List<Accent> accents = new ArrayList<Accent>();
        for (Document doc : documents){
            ObjectId objectId = (ObjectId) doc.get("_id");
            accents.add(new Accent(objectId.toString(), doc.get("name").toString()));
        }
        return accents;
    }

    public static void createAccentCheckExists(Accent accent){
        List<Accent> accents = getAccents();
        boolean isExists = false;
        for (Accent ac : accents){
            if (ac.getName().equals(accent.getName())){
                isExists = true;
                break;
            }
        }
        if (isExists){
            return;
        }
        Document document = new Document();
        document.put("name", accent.getName());
        mongoCollection.insertOne(document);
    }


}
