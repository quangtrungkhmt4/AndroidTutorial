package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Accent;
import model.Author;
import model.Categoty;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryRepository {

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

        if (!mongoDatabase.listCollectionNames().into(new ArrayList<String>()).contains("category")){
            mongoDatabase.createCollection("category");
        }

        mongoCollection = mongoDatabase.getCollection("category");
    }

    public static void createAccent(Categoty categoty, String link){
        Document document = new Document();
        document.put("name", categoty.getName());
        document.put("link", link);
        mongoCollection.insertOne(document);
    }

    public static List<Categoty> getCategories(){
        Iterable<Document> documents = mongoCollection.find();
        List<Categoty> categoties = new ArrayList<Categoty>();
        for (Document doc : documents){
            categoties.add(new Categoty(doc.get("name").toString(), doc.getString("link")));
        }
        return categoties;
    }
}
