package de.mixelblocks.core.player;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.economy.EconomyPlayerData;
import de.mixelblocks.core.objects.RedisKey;
import de.mixelblocks.core.permissions.PermissionManager;
import de.mixelblocks.core.util.Base64Util;
import net.kyori.adventure.text.Component;
import org.bson.Document;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CorePlayerImpl implements CorePlayer {

    private final CoreOfflinePlayer offlinePlayer;

    private final String syncGroup;

    private boolean synchronizedPlayer;

    public CorePlayerImpl(CoreOfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.syncGroup = MixelCorePlugin.getInstance().getConfig().getString("syncGroup", "internal");
    }

    @Override
    public OfflinePlayer offline() {
        return offlinePlayer.offline();
    }

    @Override
    public Player online() {
        return offlinePlayer.online();
    }

    @Override
    public CorePlayerSettings settings() {
        return offlinePlayer.settings();
    }

    @Override
    public EconomyPlayerData economy() {
        return offlinePlayer.economy();
    }

    @Override
    public UUID uuid() {
        return offlinePlayer.uuid();
    }

    @Override
    public String username() {
        return offlinePlayer.username();
    }

    @Override
    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }

    @Override
    public boolean isOn() {
        return offlinePlayer.isOn();
    }

    @Override
    public boolean isSynchronized() {
        return synchronizedPlayer;
    }

    @Override
    public boolean synced() {
        return synchronizedPlayer;
    }

    @Override
    public void setSynchronizedPlayer(boolean synchronizedPlayer) {
        this.synchronizedPlayer = synchronizedPlayer;
    }

    @Override
    public synchronized boolean savePlayerData() {
        try {

            Player online = online();
            String uuid = online.getUniqueId().toString();

            String inventory = Base64Util.InventorySerializer.serialize(online.getInventory(), InventoryType.PLAYER);
            String enderChest = Base64Util.InventorySerializer.serialize(online.getEnderChest(), InventoryType.ENDER_CHEST);
            String potionEffects = Base64Util.PotionEffectsSerializer.serialize(online.getActivePotionEffects());

            int experience = online.getTotalExperience();

            int gameMode = 0;
            switch(online.getGameMode()) {
                case SURVIVAL: gameMode = 0; break;
                case CREATIVE: gameMode = 1; break;
                case ADVENTURE: gameMode = 2; break;
                case SPECTATOR: gameMode = 3; break;
            }

            int finalGameMode = gameMode;

            new Thread(() -> {

                long expiryAfterThirtyMinutes = 60*30;
                MixelCorePlugin.getInstance().redis().setEx(expiryAfterThirtyMinutes, RedisKey.PLAYER_INVENTORY.getKey() + "." + syncGroup + "." + uuid, inventory);
                MixelCorePlugin.getInstance().redis().setEx(expiryAfterThirtyMinutes, RedisKey.PLAYER_ENDER_CHEST.getKey() + "." + syncGroup + "." + uuid, enderChest);
                MixelCorePlugin.getInstance().redis().setEx(expiryAfterThirtyMinutes, RedisKey.PLAYER_POTION_EFFECTS.getKey() + "." + syncGroup + "." + uuid, potionEffects);
                MixelCorePlugin.getInstance().redis().setEx(expiryAfterThirtyMinutes, RedisKey.PLAYER_XP.getKey() + "." + syncGroup + "." + uuid, Integer.toString(experience));
                MixelCorePlugin.getInstance().redis().setEx(expiryAfterThirtyMinutes, RedisKey.PLAYER_GAME_MODE.getKey() + "." + syncGroup + "." + uuid, Integer.toString(finalGameMode));

                Document playerData = MixelCorePlugin.getInstance().db().getDocument("player_data", syncGroup + "." + uuid);
                if(playerData == null) {
                    playerData = MixelCorePlugin.getInstance().db().buildDocument(syncGroup + "." + uuid, new Object[][] {
                            {
                                    RedisKey.PLAYER_INVENTORY.getKey(), inventory
                            },
                            {
                                    RedisKey.PLAYER_ENDER_CHEST.getKey(), enderChest
                            },
                            {
                                    RedisKey.PLAYER_POTION_EFFECTS.getKey(), potionEffects
                            },
                            {
                                    RedisKey.PLAYER_XP.getKey(), experience
                            },
                            {
                                    RedisKey.PLAYER_GAME_MODE.getKey(), finalGameMode
                            }
                    });
                    MixelCorePlugin.getInstance().db().insertDocument("player_data", playerData);
                } else {
                    playerData = MixelCorePlugin.getInstance().db().buildDocument(syncGroup + "." + uuid, new Object[][] {
                            {
                                    RedisKey.PLAYER_INVENTORY.getKey(), inventory
                            },
                            {
                                    RedisKey.PLAYER_ENDER_CHEST.getKey(), enderChest
                            },
                            {
                                    RedisKey.PLAYER_POTION_EFFECTS.getKey(), potionEffects
                            },
                            {
                                    RedisKey.PLAYER_XP.getKey(), experience
                            },
                            {
                                    RedisKey.PLAYER_GAME_MODE.getKey(), finalGameMode
                            }
                    });
                    MixelCorePlugin.getInstance().db().replaceDocument("player_data", syncGroup + "." + uuid, playerData);
                }
            }, "PlayerSync").start();

        } catch(Exception e) {
            MixelCorePlugin.getInstance().getLogger().warning("Failed saving playerdata (Sync)\n" + e.getStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public synchronized boolean loadPlayerData() {
        try {

            Player online = online();
            String uuid = online.getUniqueId().toString();

            Document playerData = MixelCorePlugin.getInstance().db().getDocument("player_data", syncGroup + "." + uuid);

            if(MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_INVENTORY.getKey() + "." + syncGroup + "." + uuid) == null
                    && playerData == null) {
                return true;
            }

            online.sendMessage(Component.text(MixelCorePlugin.prefix + "§aDownloading Data..."));

            String inventoryData = MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_INVENTORY.getKey() + "." + syncGroup + "." + uuid);
            String enderChestData = MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_ENDER_CHEST.getKey() + "." + syncGroup + "." + uuid);
            String effectsData = MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_POTION_EFFECTS.getKey() + "." + syncGroup + "." + uuid);
            String gameModeString = MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_GAME_MODE.getKey() + "." + syncGroup + "." + uuid);
            String experience = MixelCorePlugin.getInstance().redis().get(RedisKey.PLAYER_XP.getKey() + "." + syncGroup + "." + uuid);

            if(inventoryData == null) {
                inventoryData = playerData.getString(RedisKey.PLAYER_INVENTORY.getKey());
            }
            if(enderChestData == null) {
                enderChestData = playerData.getString(RedisKey.PLAYER_ENDER_CHEST.getKey());
            }
            if(effectsData == null) {
                effectsData = playerData.getString(RedisKey.PLAYER_POTION_EFFECTS.getKey());
            }
            if(gameModeString == null) {
                gameModeString = Integer.toString(playerData.getInteger(RedisKey.PLAYER_GAME_MODE.getKey(), 0));
            }
            if(experience == null) {
                experience = Integer.toString(playerData.getInteger(RedisKey.PLAYER_XP.getKey(), 0));
            }

            Inventory inventory = Base64Util.InventorySerializer.deserialize(inventoryData, InventoryType.PLAYER);
            Inventory enderChest = Base64Util.InventorySerializer.deserialize(enderChestData, InventoryType.ENDER_CHEST);

            online.getInventory().setContents(inventory.getContents());
            online.getEnderChest().setContents(enderChest.getContents());

            for(PotionEffect effect : Base64Util.PotionEffectsSerializer.deserialize(effectsData))
                online.addPotionEffect(effect);

            online.setTotalExperience(Integer.valueOf(experience));

            int gameMode = Integer.valueOf(gameModeString);
            switch(gameMode) {
                case 1: online.setGameMode(GameMode.CREATIVE); break;
                case 2: online.setGameMode(GameMode.ADVENTURE); break;
                case 3: online.setGameMode(GameMode.SPECTATOR); break;
                default: online.setGameMode(GameMode.SURVIVAL); break;
            }

        } catch(Exception e) {
            MixelCorePlugin.getInstance().getLogger().warning("Failed loading playerdata (Sync)\n" + e.getStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public void hidePlayer() {
        PermissionManager perms = MixelCorePlugin.getInstance().getApiImplementation().permissionManager();
        // TODO Check group weight ( when lower this gets hided )
    }

    @Override
    public void showPlayer() {
        PermissionManager perms = MixelCorePlugin.getInstance().getApiImplementation().permissionManager();
        // TODO show player to all players
    }

    @Override
    public void connectServer(String serverName) {
        MixelCorePlugin.api().proxyManager().connect(this, serverName);
    }

}
