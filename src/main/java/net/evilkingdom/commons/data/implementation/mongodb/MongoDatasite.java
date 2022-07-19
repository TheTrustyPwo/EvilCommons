package net.evilkingdom.commons.data.implementation.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import net.evilkingdom.commons.data.DataImplementor;
import net.evilkingdom.commons.data.objects.Datasite;
import org.bukkit.plugin.java.JavaPlugin;

public class MongoDatasite extends Datasite {

    private MongoClient mongoClient;

    /**
     * Allows you to create a datasite for a plugin.
     * This is used for datasites that don't require any extra parameters.
     *
     * @param plugin ~ The plugin the datasite is for.
     * @param name   ~ The name of the datasite.
     */
    public MongoDatasite(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    /**
     * Allows you to create a datasite for a plugin.
     * This is used for datasites that requires any extra parameters.
     *
     * @param plugin     ~ The plugin the datasite is for.
     * @param name       ~ The name of the datasite.
     * @param parameters ~ Any extra parameters the datasite will need.
     */
    public MongoDatasite(JavaPlugin plugin, String name, String[] parameters) {
        super(plugin, name, parameters);
    }

    /**
     * Allows you to initialize the datasite.
     */
    @Override
    public void initialize() {
        final DataImplementor implementor = DataImplementor.get(this.plugin);
        implementor.getSites().add(this);
        this.mongoClient = MongoClients.create(parameters[0]);
    }

    /**
     * Allows you to terminate the datasite.
     */
    @Override
    public void terminate() {
        this.mongoClient.close();
    }

    /**
     * Allows you to retrieve the datasite's mongo client.
     * This will only be used if the datasite's type is MONGO_DATABASE.
     *
     * @return The datasite's mongo client.
     */
    public MongoClient getMongoClient() {
        return this.mongoClient;
    }
}
