package ca.minecore.elementalarmor.gui;

import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.entity.Player;

abstract public class AbstractEnigmaGUI {
    /**
     * A method to get the GUI of a given GUI Page
     *
     * @param player a player for the placeholders and such of the gui
     * @return the gui
     */
    public abstract Gui getGui(Player player);

}
