package me.keano.factions.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.keano.factions.Factions;
import org.bson.Document;

@Getter
public class MongoHandler {

    private final MongoDatabase mongoDatabase;
    public static MongoCollection<Document> users, factions, timers, warps;

    public MongoHandler(Factions instance) {
        MongoClient client = MongoClients.create(instance.getConfig().getString("MONGO.URI"));
        this.mongoDatabase = client.getDatabase(instance.getConfig().getString("MONGO.NAME"));
        users = mongoDatabase.getCollection("users");
        factions = mongoDatabase.getCollection("factions");
        timers = mongoDatabase.getCollection("timers");
        warps = mongoDatabase.getCollection("warps");
    }
}