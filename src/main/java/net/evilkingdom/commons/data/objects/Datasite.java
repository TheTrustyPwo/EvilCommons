package net.evilkingdom.commons.data.objects;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public abstract class Datasite {

    protected final JavaPlugin plugin;

    protected final String name;
    protected final String[] parameters;
    protected final ArrayList<Datapoint> points;

    /**
     * Allows you to create a datasite for a plugin.
     * This is used for datasites that don't require any extra parameters.
     *
     * @param plugin ~ The plugin the datasite is for.
     * @param name   ~ The name of the datasite.
     */
    public Datasite(final JavaPlugin plugin, final String name) {
        this.plugin = plugin;
        this.name = name;
        this.parameters = new String[]{};
        this.points = new ArrayList<>();
    }

    /**
     * Allows you to create a datasite for a plugin.
     * This is used for datasites that requires any extra parameters.
     *
     * @param plugin     ~ The plugin the datasite is for.
     * @param name       ~ The name of the datasite.
     * @param parameters ~ Any extra parameters the datasite will need.
     */
    public Datasite(final JavaPlugin plugin, final String name, final String[] parameters) {
        this.plugin = plugin;
        this.name = name;
        this.parameters = parameters;
        this.points = new ArrayList<>();
    }

    /**
     * Allows you to initialize the datasite.
     */
    public abstract void initialize();

    /**
     * Allows you to terminate the datasite.
     */
    public abstract void terminate();

    /**
     * Allows you to retrieve the datasite's name.
     *
     * @return The datasite's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Allows you to retrieve the datasite's plugin.
     *
     * @return The datasite's plugin.
     */
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Allows you to retrieve the datasite's points.
     *
     * @return The datasite's points.
     */
    public ArrayList<Datapoint> getPoints() {
        return this.points;
    }

    /**
     * Allows you to retrieve the datasite's parameters
     *
     * @return The datasite's parameters
     */
    public String[] getParameters() {
        return parameters;
    }
}
