package de.mixelblocks.core.economy;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum Action {
    WITHDRAW_MONEY("WITHDRAW_MONEY"),
    DEPOSIT_MONEY("DEPOSIT_MONEY"),
    SET_MONEY("SET_MONEY"),
    TRANSFER_MONEY("TRANSFER_MONEY");

    private String name;

    Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
