package net.evilkingdom.commons.data.implementation.json;

import net.evilkingdom.commons.data.DataImplementor;
import net.evilkingdom.commons.data.objects.Datasite;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JsonDatasite extends Datasite {

    /**
     * Allows you to create a datasite for a plugin.
     * This is used for datasites that don't require any extra parameters.
     *
     * @param plugin ~ The plugin the datasite is for.
     * @param name   ~ The name of the datasite.
     */
    public JsonDatasite(JavaPlugin plugin, String name) {
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
    public JsonDatasite(JavaPlugin plugin, String name, String[] parameters) {
        super(plugin, name, parameters);
    }

    /**
     * Allows you to initialize the datasite.
     */
    @Override
    public void initialize() {
        final DataImplementor implementor = DataImplementor.get(this.plugin);
        implementor.getSites().add(this);
        final File dataFolder = new File(this.plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    /**
     * Allows you to terminate the datasite.
     */
    @Override
    public void terminate() {
    }
}
