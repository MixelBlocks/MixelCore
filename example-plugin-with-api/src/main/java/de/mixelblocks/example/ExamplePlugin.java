package de.mixelblocks.example;

import de.mixelblocks.core.MixelCore;
import de.mixelblocks.core.MixelCoreAPI;
import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.economy.EconomyPlayerData;
import de.mixelblocks.core.economy.EconomySystem;
import de.mixelblocks.core.player.CoreOfflinePlayer;
import de.mixelblocks.core.player.CorePlayer;
import de.mixelblocks.core.player.CorePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class ExamplePlugin extends JavaPlugin {

    private MixelCore mixelCoreAPI;

    @Override
    public void onEnable() {

        // static access to the API ( Uses the same method as the method under this but returns null and doesn't throw an exception )
        MixelCore api = MixelCoreAPI.get();

        // get the api as bukkit service
        RegisteredServiceProvider<MixelCore> registeredService = Bukkit.getServicesManager().getRegistration(MixelCore.class);
        if (registeredService.getProvider() != null) {
            this.mixelCoreAPI = registeredService.getProvider();
        } else throw new RuntimeException("Cannot get MixelCoreAPI");

        // get the economy system provider
        EconomySystem economySystem = mixelCoreAPI.economySystem();

        // resolve economy player data
        EconomyPlayerData economyPlayerData = economySystem.playerData(UUID.fromString("uuid"));

        // settings bank account
        economyPlayerData.add(EconomyPlayerData.ManagedType.BANK, 5L, "reason shown in logs");
        economyPlayerData.addBank(5L, "reason shown in logs");
        economyPlayerData.rm(EconomyPlayerData.ManagedType.BANK, 5L, "reason shown in logs");
        economyPlayerData.removeBank(5L, "reason shown in logs");
        economyPlayerData.set(EconomyPlayerData.ManagedType.BANK, 5L, "reason shown in logs");
        economyPlayerData.setBank(5L, "reason shown in logs");

        long bankAmount = economyPlayerData.getBank();

        // settings cash
        economyPlayerData.add(EconomyPlayerData.ManagedType.CASH, 5L, null);
        economyPlayerData.addMoney(5L);
        economyPlayerData.rm(EconomyPlayerData.ManagedType.CASH, 5L, null);
        economyPlayerData.removeMoney(5L);
        economyPlayerData.set(EconomyPlayerData.ManagedType.CASH, 5L, null);
        economyPlayerData.setMoney(5L);

        long cashAmount = economyPlayerData.getMoney();

        // get the economy player manager provider
        CorePlayerManager playerManager = mixelCoreAPI.playerManager();

        // resolve all online CorePlayer 's
        Map<UUID, CorePlayer> onlinePlayers = playerManager.allOnline();

        // resolve CoreOfflinePlayer
        CoreOfflinePlayer offlinePlayer = playerManager.offline(UUID.fromString("uuid"));

        // get economy data from offline core player
        economyPlayerData = offlinePlayer.economy();

        // resolve online player ( returns null if offline )
        CorePlayer onlinePlayer = playerManager.online(UUID.fromString("uuid"));

        // get the economy player manager provider
        ProxyManager proxyManager = mixelCoreAPI.proxyManager();

        // connect a CorePlayer to another server
        proxyManager.connect(onlinePlayer, "citybuild-1");

        // connect a Bukkit Player to another server
        proxyManager.connect(onlinePlayer.online(), "citybuild-1");


    }

    public MixelCore getMixelCoreAPI() {
        return mixelCoreAPI;
    }

}
