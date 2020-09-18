package ca.minecore.elementalarmor.gui;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.gui.pages.HomePage;
import org.bukkit.entity.Player;

/**
 * Inventory Manager class
 * will have a reference to all the different inventories, and will have an instance in the Main Class
 * <p>
 */
public class InventoryManager {

    private Main plugin;
    private final HomePage homePage;

    public InventoryManager(Main plugin) {
        this.plugin = plugin;
        homePage = new HomePage();
    }

    /**
     * Method to open any of the GUI's
     * @param gui - the AbstractEnigmaGUI that we want to open
     * @param player - the player who we are opening it for
     */
    public void openGui(AbstractEnigmaGUI gui, Player player) {
        gui.getGui(player).open(player);
    }

    // return the homepage class where we can call
    public HomePage getHomePage() {
        return homePage;
    }

}
