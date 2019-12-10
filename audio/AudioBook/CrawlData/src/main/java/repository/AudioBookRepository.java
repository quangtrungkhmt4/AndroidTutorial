package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.AudioBook;
import model.Author;
import model.Part;
import org.bson.Document;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AudioBookRepository {

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

        if (!mongoDatabase.listCollectionNames().into(new ArrayList<String>()).contains("book")){
            mongoDatabase.createCollection("book");
        }

        mongoCollection = mongoDatabase.getCollection("book");
    }

    public static void createBook(AudioBook audioBook){
        Document document = new Document();
        document.put("name", audioBook.getName());
        List<Document> documents = new ArrayList<Document>();
        for (Part p : audioBook.getParts()){
            Document doc = new Document();
            doc.put("name", p.getName());
            doc.put("url", p.getUrl());
            documents.add(doc);
        }
        document.put("part", documents);
        document.put("author", audioBook.getIdAuthor());
        document.put("accent", audioBook.getIdAccent());
        document.put("image", audioBook.getImage());
        document.put("duration", audioBook.getDuration());
        document.put("number_part", audioBook.getNumberOfPart());
        document.put("views", audioBook.getViews());
        document.put("category", audioBook.getIdCategory());
        document.put("created_at", audioBook.getCreatedAt());
        document.put("related_audio", audioBook.getRelatedAudio());
        mongoCollection.insertOne(document);
    }
}
