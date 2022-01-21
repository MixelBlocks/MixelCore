package de.mixelblocks.core.permissions;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.configuration.Config;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.SortedMap;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class PermissionManagerImpl implements PermissionManager {

    private final MixelCorePlugin plugin;

    private final LuckPerms luckPerms;

    private final Config chatFormatConfig;

    public PermissionManagerImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        luckPerms = provider.getProvider();
        chatFormatConfig = plugin.getChatFormatConfig();
    }

    @Override
    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    @Override
    public String getDefaultPlayerGroupId(Player player) {
        return getDefaultPlayerGroupId(player.getUniqueId());
    }

    @Override
    public String getDefaultPlayerGroupId(UUID uuid) {
        try {
            return luckPerms.getUserManager().getUser(uuid).getPrimaryGroup();
        } catch(Exception except) {
            return null;
        }
    }

    @Override
    public String resolveGroupPrefix(String groupId) {
        return chatFormatConfig.get().getString(groupId, "");
    }

    @Override
    public String resolvePlayerGroupPrefix(Player player) {
        return resolvePlayerGroupPrefix(player.getUniqueId());
    }

    @Override
    public String resolvePlayerGroupPrefix(UUID uuid) {
        return resolveGroupPrefix(getDefaultPlayerGroupId(uuid));
    }

    @Override
    public boolean isHigherGroup(String group_should_be_higher, String group_should_be_lower) {
        int higher = luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight().getAsInt() : 0;
        int lower = luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight().getAsInt() : 0;
        if(higher <= lower) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isHigherPlayer(Player player_should_be_higher, Player player_should_be_lower) {
        return isHigherGroup(resolvePlayerGroupPrefix(player_should_be_higher), resolvePlayerGroupPrefix(player_should_be_lower));
    }

    @Override
    public CachedMetaData playerMeta(Player player) {
        return loadUser(player).getCachedData().getMetaData();
    }

    @Override
    public CachedMetaData groupMeta(String group) {
        return getLuckPerms().getGroupManager().getGroup(group).getCachedData().getMetaData();
    }

    @Override
    public User loadUser(Player player) {
        if (!player.isOnline()) return null;
        return getLuckPerms().getUserManager().getUser(player.getUniqueId());
    }

    @Override
    public Group loadUser(String group) {
        return getLuckPerms().getGroupManager().getGroup(group);
    }

    @Override
    public String getPrefix(Player player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    @Override
    public String getSuffix(Player player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    @Override
    public String getPrefixes(Player player) {
        SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        StringBuilder prefixes = new StringBuilder();
        for (String prefix : map.values())
            prefixes.append(prefix);
        return prefixes.toString();
    }

    @Override
    public String getSuffixes(Player player) {
        SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        StringBuilder suffixes = new StringBuilder();
        for (String prefix : map.values())
            suffixes.append(prefix);
        return suffixes.toString();
    }

}
