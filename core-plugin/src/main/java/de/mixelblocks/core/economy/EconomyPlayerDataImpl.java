package de.mixelblocks.core.economy;

import de.mixelblocks.core.player.CoreOfflinePlayer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class EconomyPlayerDataImpl implements EconomyPlayerData {

    private final CoreOfflinePlayer player;

    public EconomyPlayerDataImpl(CoreOfflinePlayer player) {
        this.player = player;
    }

    @Override
    public boolean addMoney(long amount) {
        return false;
    }

    @Override
    public boolean removeMoney(long amount) {
        return false;
    }

    @Override
    public boolean setMoney(long amount) {
        return false;
    }

    @Override
    public long getMoney() {
        return 0;
    }

    @Override
    public boolean addBank(long amount, String description) {
        return false;
    }

    @Override
    public boolean removeBank(long amount, String description) {
        return false;
    }

    @Override
    public boolean setBank(long amount, String description) {
        return false;
    }

    @Override
    public long getBank() {
        return 0;
    }
}
