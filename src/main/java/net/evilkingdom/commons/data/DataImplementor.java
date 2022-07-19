package net.evilkingdom.commons.data;

import net.evilkingdom.commons.data.objects.Datasite;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;

public class DataImplementor {

    private static final HashSet<DataImplementor> cache = new HashSet<>();
    private final JavaPlugin plugin;
    private final HashSet<Datasite> sites;

    /**
     * Allows you to create a DataImplementor.
     * This should not be used inside your plugin whatsoever!
     *
     * @param plugin ~ The plugin to create the DataImplementor for.
     */
    public DataImplementor(final JavaPlugin plugin) {
        this.plugin = plugin;

        this.sites = new HashSet<>();

        cache.add(this);
    }

    /**
     * Allows you to retrieve the DataImplementor for a plugin.
     * It will either pull it out of the cache or look for one if it doesn't exist.
     *
     * @param plugin ~ The plugin the DataImplementor is for.
     * @return The self class.
     */
    public static DataImplementor get(final JavaPlugin plugin) {
        final Optional<DataImplementor> optionalImplementor = cache
                .stream()
                .filter(implementor -> implementor.getPlugin() == plugin)
                .findFirst();
        return optionalImplementor.orElseGet(() -> new DataImplementor(plugin));
    }

    /**
     * Allows you to retrieve the implementor's plugin.
     *
     * @return The implementor's plugin.
     */
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Allows you to retrieve the implementor's sites.
     *
     * @return The implementor's sites.
     */
    public HashSet<Datasite> getSites() {
        return this.sites;
    }
}
