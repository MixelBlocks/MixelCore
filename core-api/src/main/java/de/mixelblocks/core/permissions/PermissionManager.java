package de.mixelblocks.core.permissions;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface PermissionManager {

    /**
     * Get the by LuckPerms registered service provider
     * @return
     */
    LuckPerms getLuckPerms();

    /**
     * Get the default group ( higher ranked ) of a player
     * @param player
     * @return defaultGroup
     */
    String getDefaultPlayerGroupId(Player player);

    /**
     * Get the default group ( higher ranked ) of a player
     * @param uuid
     * @return defaultGroup
     */
    String getDefaultPlayerGroupId(UUID uuid);

    /**
     * resolve the groups prefix by given group
     * @param groupId
     * @return prefix
     */
    String resolveGroupPrefix(String groupId);

    /**
     * resolve the players higher groups prefix by a given player
     * @param player
     * @return prefix
     */
    String resolvePlayerGroupPrefix(Player player);

    /**
     * resolve the players higher groups prefix by a given player uuid
     * @param uuid
     * @return prefix
     */
    String resolvePlayerGroupPrefix(UUID uuid);

    /**
     * Check if a group is ranked higher
     * @param group_should_be_higher -> should be higher rank
     * @param group_should_be_lower -> should be the lower rank
     * @return boolean value if they should be higher group is ranked higher
     */
    boolean isHigherGroup(String group_should_be_higher, String group_should_be_lower);

    /**
     * Check if a player is ranked higher
     * @param player_should_be_higher -> should be higher ranked player
     * @param player_should_be_lower -> should be the lower ranked player
     * @return boolean value if they should be higher group is ranked higher
     */
    boolean isHigherPlayer(Player player_should_be_higher, Player player_should_be_lower);

    /**
     * resolve cached metadata of a player
     * @param player
     * @return data
     */
    CachedMetaData playerMeta(Player player);

    /**
     * resolve cached metadata of a group
     * @param group
     * @return data
     */
    CachedMetaData groupMeta(String group);

    /**
     * get the luckperms user object
     * @param player
     * @return user
     */
    User loadUser(Player player);

    /**
     * get the luckperms group object
     * @param group
     * @return group
     */
    Group loadUser(String group);

    /**
     * resolve the players higher ranked by weight prefix
     * @param player
     * @return prefix
     */
    String getPrefix(Player player);

    /**
     * resolve the players higher ranked by weight suffix
     * @param player
     * @return suffix
     */
    String getSuffix(Player player);

    /**
     * resolve all active player prefixes comma separated
     * @param player
     * @return prefixes
     */
    String getPrefixes(Player player);

    /**
     * resolve all active player suffixes comma separated
     * @param player
     * @return suffixes
     */
    String getSuffixes(Player player);

}
