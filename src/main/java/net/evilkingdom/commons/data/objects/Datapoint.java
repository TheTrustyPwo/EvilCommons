package net.evilkingdom.commons.data.objects;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class Datapoint {

    protected final String name;
    protected final Datasite site;

    /**
     * Allows you to create a datapoint for a plugin.
     *
     * @param datasite ~ The datasite of the datapoint.
     * @param name     ~ The type of datapoint.
     */
    public Datapoint(final Datasite datasite, final String name) {
        this.name = name;
        this.site = datasite;
    }

    /**
     * Allows you to retrieve the datapoint's site.
     *
     * @return The datapoint's site.
     */
    public Datasite getSite() {
        return this.site;
    }

    /**
     * Allows you to retrieve the datapoint's name.
     *
     * @return The datapoint's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Allows you to register the datapoint.
     */
    public abstract void register();

    /**
     * Allows you to retrieve all the json objects.
     *
     * @return All the json objects.
     */
    public abstract CompletableFuture<ArrayList<JsonObject>> getAll();

    /**
     * Allows you to retrieve the total number of json objects.
     *
     * @return The total number of json objects.
     */
    public abstract CompletableFuture<Long> countAll();

    /**
     * Allows you to retrieve a json object from an identifier.
     *
     * @param identifier ~ The identifier of the json object.
     * @return The json object.
     */
    public abstract CompletableFuture<Optional<JsonObject>> get(final String identifier);

    /**
     * Allows you to save a json object.
     *
     * @param jsonObject ~ The json object to save.
     * @param identifier ~ THe identifier of the json object.
     */
    public abstract void save(final JsonObject jsonObject, final String identifier);


    public void saveAsync(final JsonObject jsonObject, final String identifier) {
        CompletableFuture.runAsync(() -> this.save(jsonObject, identifier));
    }

    /**
     * Allows you to retrieve if a json object exists from an identifier.
     *
     * @param identifier ~ The identifier of the json object.
     * @return If a json object exists from the identifier.
     */
    public abstract CompletableFuture<Boolean> exists(final String identifier);

}
