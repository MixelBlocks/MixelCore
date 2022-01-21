package de.mixelblocks.core.objects;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum RedisKey {

    PLAYER_INVENTORY("playerInventory"),
    PLAYER_ENDER_CHEST("playerEnderChest"),
    PLAYER_XP("playerExperience"),
    PLAYER_GAME_MODE("playerGameMode"),
    PLAYER_POTION_EFFECTS("playerEffects");

    private String key;
    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key + Seperator.KEY.getCharacter();
    }

    public String getValue() {
        return key;
    }

    public static String splitValue(String fullString) {
        String[] args = fullString.split(Seperator.KEY.getCharacter());
        return args[1];
    }

}
