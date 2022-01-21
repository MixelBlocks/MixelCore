package de.mixelblocks.core.economy;

import de.mixelblocks.core.player.CorePlayer;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface EconomySystem {

    /**
     * The long name of the currency like MixelCoins
     * @return
     */
    String currencyName();

    /**
     * Short name of currency like MC ( MixelCoins in this case )
     * @return
     */
    String currency();

    /**
     * resolve the economy data of a player
     * @param player
     * @return economyData
     */
    default EconomyPlayerData playerData(CorePlayer player) {
        return player.economy();
    }

    /**
     * resolve the economy data of a player by given uuid
     * @param uuid
     * @return economyData
     */
    EconomyPlayerData playerData(UUID uuid);

}
