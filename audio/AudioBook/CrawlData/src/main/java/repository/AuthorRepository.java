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
import java.util.Collections;
import java.util.List;

public class AuthorRepository {
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

        if (!mongoDatabase.listCollectionNames().into(new ArrayList<String>()).contains("author")){
            mongoDatabase.createCollection("author");
        }

        mongoCollection = mongoDatabase.getCollection("author");
    }

    public static void createAuthor(Author author){
        Document document = new Document();
        document.put("name", author.getName());
        mongoCollection.insertOne(document);
    }

    public static List<Author> getAuthors(){
        Iterable<Document> documents = mongoCollection.find();
        List<Author> authors = new ArrayList<Author>();
        for (Document doc : documents){
            authors.add(new Author(doc.get("_id").toString(), doc.getString("name")));
        }
        return authors;
    }

    public static void createAuthorCheckExists(Author author){
        List<Author> authors = getAuthors();
        boolean isExists = false;
        for (Author au : authors){
            if (au.getName().equals(author.getName())){
                isExists = true;
                break;
            }
        }
        if (isExists){
            return;
        }
        Document document = new Document();
        document.put("name", author.getName());
        mongoCollection.insertOne(document);
    }
}
