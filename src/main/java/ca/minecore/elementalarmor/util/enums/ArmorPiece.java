package ca.minecore.elementalarmor.util.enums;

public enum ArmorPiece {
    HELMET(0, "Helmet"),
    CHESTPLATE(1, "Chestplate"),
    LEGGINGS(2, "Leggings"),
    BOOTS(3, "Boots");

    private final int index;
    private final String formattedName;

    ArmorPiece(int index, String formattedName) {
        this.index = index;
        this.formattedName = formattedName;
    }

    public int getIndex() {
        return index;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
