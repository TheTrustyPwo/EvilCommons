package net.evilkingdom.commons.utilities.luckperms;

import net.luckperms.api.LuckPermsProvider;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LuckPermsUtilities {

    /**
     * Allows you to a player's rank from their UUID.
     * This should only be used if the player is online (they'll be cached).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's rank.
     */
    public static Optional<String> getRankViaCache(final UUID uuid) {
        return Optional.ofNullable(LuckPermsProvider.get().getUserManager().getUser(uuid)
                .getCachedData().getMetaData().getPrimaryGroup());
    }

    /**
     * Allows you to a player's prefix from their uuid.
     * This should only be used if the player is online (they'll be cached).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's prefix.
     */
    public static Optional<String> getPrefixViaCache(final UUID uuid) {
        return Optional.ofNullable(LuckPermsProvider.get().getUserManager().getUser(uuid)
                .getCachedData().getMetaData().getPrefix());
    }

    /**
     * Allows you to a player's suffix from their uuid.
     * This should only be used if the player is online (they'll be cached).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's suffix.
     */
    public static Optional<String> getSuffixViaCache(final UUID uuid) {
        return Optional.ofNullable(LuckPermsProvider.get().getUserManager().getUser(uuid)
                .getCachedData().getMetaData().getSuffix());
    }

    /**
     * Allows you to a player's permissions from their uuid.
     * This should only be used if the player is online (they'll be cached).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's permissions.
     */
    public static ArrayList<String> getPermissionsViaCache(final UUID uuid) {
        return new ArrayList<>(LuckPermsProvider.get().getUserManager().getUser(uuid)
                .getCachedData().getPermissionData().getPermissionMap().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList());
    }

    /**
     * Allows you to a player's rank from their uuid.
     * This should only be used if the is not cached (or you're just being safe).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's rank.
     */
    public static CompletableFuture<Optional<String>> getRank(final UUID uuid) {
        return LuckPermsProvider.get().getUserManager()
                .loadUser(uuid)
                .thenApply(user -> Optional.ofNullable(user.getCachedData().getMetaData().getPrimaryGroup()));
    }

    /**
     * Allows you to a player's prefix from their uuid.
     * This should only be used if the is not cached (or you're just being safe).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's prefix.
     */
    public static CompletableFuture<Optional<String>> getPrefix(final UUID uuid) {
        return LuckPermsProvider.get().getUserManager()
                .loadUser(uuid)
                .thenApply(user -> Optional.ofNullable(user.getCachedData().getMetaData().getPrefix()));
    }

    /**
     * Allows you to a player's suffix from their uuid.
     * This should only be used if the is not cached (or you're just being safe).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's suffix.
     */
    public static CompletableFuture<Optional<String>> getSuffix(final UUID uuid) {
        return LuckPermsProvider.get().getUserManager()
                .loadUser(uuid)
                .thenApply(user -> Optional.ofNullable(user.getCachedData().getMetaData().getSuffix()));
    }

    /**
     * Allows you to a player's permissions from their uuid.
     * This should only be used if the is not cached (or you're just being safe).
     * Uses LuckPerms' API.
     *
     * @param uuid ~ The player's uuid.
     * @return The player's permissions.
     */
    public static CompletableFuture<ArrayList<String>> getPermissions(final UUID uuid) {
        return LuckPermsProvider.get().getUserManager()
                .loadUser(uuid)
                .thenApply(user -> new ArrayList<>(user.getCachedData()
                        .getPermissionData().getPermissionMap().entrySet().stream()
                        .filter(Map.Entry::getValue).map(Map.Entry::getKey).toList()));
    }

}
