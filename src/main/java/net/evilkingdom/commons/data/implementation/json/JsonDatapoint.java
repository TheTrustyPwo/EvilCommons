package net.evilkingdom.commons.data.implementation.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.evilkingdom.commons.data.objects.Datapoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class JsonDatapoint extends Datapoint {

    private final JsonDatasite site;
    private File folder;

    /**
     * Allows you to create a datapoint for a plugin.
     *
     * @param datasite ~ The datasite of the datapoint.
     * @param name     ~ The type of datapoint.
     */
    public JsonDatapoint(JsonDatasite datasite, String name) {
        super(datasite, name);
        this.site = datasite;
    }

    /**
     * Allows you to register the datapoint.
     */
    @Override
    public void register() {
        this.site.getPoints().add(this);
        final File dataFolder = new File(this.site.getPlugin().getDataFolder(), "data");
        this.folder = new File(dataFolder, this.name);
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
    }

    /**
     * Allows you to retrieve all the json objects.
     *
     * @return All the json objects.
     */
    @Override
    public CompletableFuture<ArrayList<JsonObject>> getAll() {
        return CompletableFuture.supplyAsync(() -> Arrays.stream(this.folder.listFiles()).map(file -> {
            String jsonString = null;
            try {
                jsonString = Files.readString(file.toPath());
            } catch (final IOException ignored) {
            }
            assert jsonString != null;
            return JsonParser.parseString(jsonString).getAsJsonObject();
        }).collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Allows you to retrieve the total number of json objects.
     *
     * @return The total number of json objects.
     */
    @Override
    public CompletableFuture<Long> countAll() {
        return CompletableFuture.supplyAsync(() -> Arrays.stream(this.folder.listFiles()).count());
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
            final Optional<File> optionalFile = Arrays.stream(this.folder.listFiles())
                    .filter(file -> file.getName().equals(identifier + ".json"))
                    .findFirst();
            if (optionalFile.isPresent()) {
                final File file = optionalFile.get();
                String jsonString = null;
                try {
                    jsonString = Files.readString(file.toPath());
                } catch (final IOException ignored) {
                }
                return Optional.of(JsonParser.parseString(jsonString).getAsJsonObject());
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
        final File file = new File(this.folder, identifier + ".json");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(jsonObject));
            fileWriter.flush();
            fileWriter.close();
        } catch (final IOException ignored) {
        }
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
            final Optional<File> optionalFile = Arrays.stream(this.folder.listFiles())
                    .filter(file -> file.getName().equals(identifier + ".json"))
                    .findFirst();
            return optionalFile.isPresent();
        });
    }
}
