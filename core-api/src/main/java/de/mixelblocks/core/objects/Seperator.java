package de.mixelblocks.core.objects;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum Seperator {

    OBJ("%"), SEP(":"), SEPLOC(";"), PART("&"), KEY("_");

    private String character;

    Seperator(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

}
