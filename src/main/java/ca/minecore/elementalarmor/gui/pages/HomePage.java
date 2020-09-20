package ca.minecore.elementalarmor.gui.pages;

import ca.minecore.elementalarmor.gui.AbstractEnigmaGUI;
import ca.minecore.elementalarmor.util.ChatUtil;
import ca.minecore.elementalarmor.util.emums.ArmorType;
import ca.minecore.elementalarmor.util.emums.Charm;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HomePage extends AbstractEnigmaGUI {

    @Override
    public Gui getGui(Player player) {
        Gui gui = new Gui(4, ChatUtil.colorize("&eMinecore &7- &bEnigma"));

        gui.setItem(4, 5, getPlayerItem(player));
        gui.setItem(2, 3, getAirItem(player));
        gui.setItem(2, 4, getEarthItem(player));
        gui.setItem(2, 5, getFireItem(player));
        gui.setItem(2, 6, getWaterItem(player));
        gui.setItem(2, 7, getMagicItem(player));

        gui.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem());

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        return gui;
    }

    /**
     * ALL BELOW ARE FOR GETTING THE DIFFERENT ITEM FOR DIFERENT PLAYERS
     */

    private GuiItem getPlayerItem(Player player) {
        Material mat = Material.PLAYER_HEAD;
        return ItemBuilder.from(mat).setSkullOwner(player).setName(ChatUtil.colorize("&3Your Profile"))
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    private GuiItem getAirItem(Player player) {
        Material mat = Material.FEATHER;
        return ItemBuilder.from(mat).setName(ChatUtil.colorize("<#e0fffe>Air Collection"))
                .setLore(
                        generateCompletionLore(player, ArmorType.AIR)
                )
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    private GuiItem getFireItem(Player player) {
        Material mat = Material.BLAZE_POWDER;
        return ItemBuilder.from(mat).setName(ChatUtil.colorize("<#E25822>Fire Collection"))
                .setLore(
                        generateCompletionLore(player, ArmorType.FIRE)
                )
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    private GuiItem getWaterItem(Player player) {
        Material mat = Material.WATER_BUCKET;
        return ItemBuilder.from(mat).setName(ChatUtil.colorize("<#A6E7FF>Water Collection"))
                .setLore(
                        generateCompletionLore(player, ArmorType.WATER)
                )
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    private GuiItem getEarthItem(Player player) {
        Material mat = Material.DIRT;
        return ItemBuilder.from(mat).setName(ChatUtil.colorize("<#CD853F>Earth Collection"))
                .setLore(
                        generateCompletionLore(player, ArmorType.EARTH)
                )
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    private GuiItem getMagicItem(Player player) {
        Material mat = Material.ENDER_EYE;
        return ItemBuilder.from(mat).setName(ChatUtil.colorize("<#7851A9>Magic Collection"))
                .setLore(
                        generateCompletionLore(player, ArmorType.MAGIC)
                )
                .asGuiItem(event -> {
                    // handle click event
                });
    }

    /**
     * An example progress bar
     *
     * @param player the player who the progress is for
     * @return a string of their progress for a given collection
     */
    private String generateCompletionLore(Player player, ArmorType type) {
        String redColor = "<#93291E>";
        String greenColor = "<#00ff9d>";
        return ChatUtil.colorize(
                "&6Progress &7» &8[ {green}|||||||{red}||||||||||||| &8]"
                        .replace("{green}", greenColor)
                        .replace("{red}", redColor)
        );
    }
}
