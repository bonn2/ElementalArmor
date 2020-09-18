package ca.minecore.elementalarmor.gui.pages;

import ca.minecore.elementalarmor.gui.AbstractEnigmaGUI;
import ca.minecore.elementalarmor.util.ChatUtil;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.entity.Player;

public class HomePage extends AbstractEnigmaGUI {

    @Override
    public Gui getGui(Player player) {
        Gui gui = new Gui(5, ChatUtil.colorize("&eMinecore &7- &bEnigma"));

        return gui;
    }

}
