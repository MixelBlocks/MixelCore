package de.mixelblocks.core.economy;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum EcoSep {

    A("_"), B(":"), C(";");
    private String character;

    EcoSep(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

}
