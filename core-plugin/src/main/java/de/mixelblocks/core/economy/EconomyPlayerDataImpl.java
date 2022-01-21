package de.mixelblocks.core.economy;

import de.mixelblocks.core.MixelCoreAPI;
import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.player.CoreOfflinePlayer;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class EconomyPlayerDataImpl implements EconomyPlayerData {

    private final CoreOfflinePlayer player;

    private Document economyPlayerDocument;

    public EconomyPlayerDataImpl(CoreOfflinePlayer player) {
        this.player = player;
        this.economyPlayerDocument = MixelCoreAPI.get().economySystem().db().getDocument("user_accounts", player.uuid().toString());
        if(this.economyPlayerDocument == null) {
            this.economyPlayerDocument = MixelCoreAPI.get().economySystem().db().buildDocument(player.uuid().toString(), new Object[][] {
                    {
                            "money", MixelCorePlugin.getInstance().getConfig().getLong("economy.startAmount", 0L)
                    },
                    {
                            "bank", 0L
                    }
            });
            MixelCoreAPI.get().economySystem().db().insertDocument("user_accounts", this.economyPlayerDocument);
        }
    }

    @Override
    public boolean addMoney(long amount) {
        if(amount < 0) return false;
        try {
            long newValue = getBank() + amount;
            economyPlayerDocument.put("money", newValue);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        return true;
    }

    @Override
    public boolean removeMoney(long amount) {
        if(amount < 0) return false;
        try {
            long newValue = getBank() - amount;
            economyPlayerDocument.put("money", newValue);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        return true;
    }

    @Override
    public boolean setMoney(long amount) {
        try {
            economyPlayerDocument.put("money", amount);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        return true;
    }

    @Override
    public long getMoney() {
        return economyPlayerDocument.getLong("money");
    }

    @Override
    public boolean addBank(long amount, @NotNull String description) {
        if(amount < 0) return false;
        try {
            long newValue = getBank() + amount;
            economyPlayerDocument.put("bank", newValue);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        this.addAction(player.online().getUniqueId(), Action.DEPOSIT_MONEY, amount, description);
        return true;
    }

    @Override
    public boolean removeBank(long amount, @NotNull String description) {
        if(amount < 0) return false;
        try {
            long newValue = getBank() - amount;
            economyPlayerDocument.put("bank", newValue);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        this.addAction(player.online().getUniqueId(), Action.WITHDRAW_MONEY, amount, description);
        return true;
    }

    @Override
    public boolean setBank(long amount, @NotNull String description) {
        try {
            economyPlayerDocument.put("bank", amount);
            updatePlayerDocument();
        } catch(Exception e) {return false;}
        this.addAction(player.online().getUniqueId(), Action.SET_MONEY, amount, description);
        return true;
    }

    @Override
    public long getBank() {
        return economyPlayerDocument.getLong("bank");
    }

    private void addAction(UUID player, Action action, long amount, String description) {
        long time = System.currentTimeMillis();
        MixelCoreAPI.get().economySystem().db().insertDocument("user_transactions", MixelCoreAPI.get().economySystem().db().buildDocument(player.toString(), new Object[][] {
                {
                        "action", action.getName()
                },
                {
                        "amount", amount
                },
                {
                        "description", description
                },
                {
                        "createdAt", time
                }
        }));
    }

    private void updatePlayerDocument() {
        MixelCoreAPI.get().economySystem().db().replaceDocument("user_accounts", player.online().getUniqueId().toString(), economyPlayerDocument);
    }

}
