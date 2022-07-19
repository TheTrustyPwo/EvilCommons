package net.evilkingdom.commons.data.implementation.mongodb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import net.evilkingdom.commons.data.objects.Datapoint;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MongoDatapoint extends Datapoint {

    private final MongoDatasite site;
    private MongoCollection<Document> collection;

    /**
     * Allows you to create a datapoint for a plugin.
     *
     * @param datasite ~ The datasite of the datapoint.
     * @param name     ~ The type of datapoint.
     */
    public MongoDatapoint(MongoDatasite datasite, String name) {
        super(datasite, name);
        this.site = datasite;
    }

    /**
     * Allows you to register the datapoint.
     */
    @Override
    public void register() {
        this.site.getPoints().add(this);
        final MongoDatabase mongoDatabase = this.site.getMongoClient().getDatabase(this.site.getName());
        if (!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(this.name)) {
            mongoDatabase.createCollection(this.name);
        }
        this.collection = this.site.getMongoClient().getDatabase(this.site.getName()).getCollection(this.name);
    }

    /**
     * Allows you to retrieve all the json objects.
     *
     * @return All the json objects.
     */
    @Override
    public CompletableFuture<ArrayList<JsonObject>> getAll() {
        return CompletableFuture.supplyAsync(() ->
                this.collection.find().into(new ArrayList<>())
                        .stream()
                        .map(document -> JsonParser.parseString(document.toJson(
                                JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()
                        )).getAsJsonObject())
                        .collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Allows you to retrieve the total number of json objects.
     *
     * @return The total number of json objects.
     */
    @Override
    public CompletableFuture<Long> countAll() {
        return CompletableFuture.supplyAsync(() -> this.collection.countDocuments());
    }

    /**
     * Allows you to retrieve a json object from an identifier.
     *
     * @param identifier ~ The identifier of the json object.
     * @return The json object.
     */
    @Override
    public CompletableFuture<Optional<JsonObject>> get(String identifier) {
        return CompletableFuture.supplyAsync(() -> {
            final Optional<Document> optionalDocument = Optional.ofNullable(
                    this.collection.find(Filters.eq("_id", identifier)).first());
            if (optionalDocument.isPresent()) {
                final Document document = optionalDocument.get();
                return Optional.of(JsonParser.parseString(
                                document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()))
                        .getAsJsonObject());
            }
            return Optional.empty();
        });
    }

    /**
     * Allows you to save a json object.
     *
     * @param jsonObject ~ The json object to save.
     * @param identifier ~ The identifier of the json object.
     */
    @Override
    public void save(JsonObject jsonObject, String identifier) {
        final Document document = Document.parse(new Gson().toJson(jsonObject));
        CompletableFuture.runAsync(() ->
                this.collection.findOneAndReplace(Filters.eq("_id", identifier), document,
                        new FindOneAndReplaceOptions().upsert(true)));
    }

    /**
     * Allows you to retrieve if a json object exists from an identifier.
     *
     * @param identifier ~ The identifier of the json object.
     * @return If a json object exists from the identifier.
     */
    @Override
    public CompletableFuture<Boolean> exists(String identifier) {
        return CompletableFuture.supplyAsync(() -> {
            final Optional<Document> optionalDocument = Optional.ofNullable(
                    this.collection.find(Filters.eq("_id", identifier)).first());
            return optionalDocument.isPresent();
        });
    }
}
