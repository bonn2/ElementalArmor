package ca.minecore.elementalarmor.util.exceptions;

import ca.minecore.elementalarmor.util.enums.ArmorType;
import org.bukkit.ChatColor;

public class InvalidCharmException extends Exception {

    /**
     * Thrown when a charm is put onto an incompatible piece of armor
     * @param charmType The ArmorType the charm is compatible with
     * @param armorType The ArmorType it was attempted to be placed on
     */
    public InvalidCharmException(ArmorType charmType, ArmorType armorType) {
        super(ChatColor.RED + charmType.name() + " charm cannot be put on " + armorType.name() + " armor!");
    }
}
