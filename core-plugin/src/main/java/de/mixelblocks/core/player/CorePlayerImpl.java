package de.mixelblocks.core.player;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.economy.EconomyPlayerData;
import de.mixelblocks.core.objects.RedisKey;
import de.mixelblocks.core.permissions.PermissionManager;
import de.mixelblocks.core.util.Base64Util;
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

            int heldItemSlot = online.getInventory().getHeldItemSlot();

            new Thread(() -> {
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
                            },
                            {
                                    RedisKey.HELD_ITEM_SLOT.getKey(), heldItemSlot
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
                            },
                            {
                                    RedisKey.HELD_ITEM_SLOT.getKey(), heldItemSlot
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

            if(playerData == null) {
                return true;
            }

            String inventoryData = playerData.getString(RedisKey.PLAYER_INVENTORY.getKey());
            String enderChestData = playerData.getString(RedisKey.PLAYER_ENDER_CHEST.getKey());
            String effectsData = playerData.getString(RedisKey.PLAYER_POTION_EFFECTS.getKey());
            String gameModeString = Integer.toString(playerData.getInteger(RedisKey.PLAYER_GAME_MODE.getKey(), 0));
            String experience = Integer.toString(playerData.getInteger(RedisKey.PLAYER_XP.getKey(), 0));
            String heldItemSlot = Integer.toString(playerData.getInteger(RedisKey.HELD_ITEM_SLOT.getKey(), 0));

            Inventory inventory = Base64Util.InventorySerializer.deserialize(inventoryData, InventoryType.PLAYER);
            Inventory enderChest = Base64Util.InventorySerializer.deserialize(enderChestData, InventoryType.ENDER_CHEST);

            online.getInventory().setContents(inventory.getContents());
            online.getEnderChest().setContents(enderChest.getContents());

            for(PotionEffect effect : Base64Util.PotionEffectsSerializer.deserialize(effectsData))
                online.addPotionEffect(effect);

            online.setTotalExperience(Integer.valueOf(experience));

            online.getInventory().setHeldItemSlot(Integer.valueOf(heldItemSlot));

            int gameMode = Integer.valueOf(gameModeString);
            switch(gameMode) {
                case 1: online.setGameMode(GameMode.CREATIVE); break;
                case 2: online.setGameMode(GameMode.ADVENTURE); break;
                case 3: online.setGameMode(GameMode.SPECTATOR); break;
                default: online.setGameMode(GameMode.SURVIVAL); break;
            }

        } catch(Exception e) {
            MixelCorePlugin.getInstance().getLogger().warning("Failed loading player data (Sync)\n" + e.getStackTrace());
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
